package org.chern.manager.security;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
* Реализация перехватчика запросов пользователей.
*/
@RequiredArgsConstructor
public class OAuthClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    // информация о текущем авторизованном пользователе
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    // идентификатор oauth для получения ключей доступа
    private final String regId;

    /**
    * Метод установки стратегий безопасности и авторизации
    * 
    * @SecurityContextHolder контекст безопасности (по умолчанию)
    * @Setter для изменения типа на другую стратегию ContextHolderStrategy (при необходимости)
    */
    @Setter
    private SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();


    /**
     * Метод-перехватчик запросов клиентов.
     * Предназначен для автоматической подстановки токена авторизации в запросы, если он отсутствует.
     * authorizedClient - собранный авторизованный клиент со значением токена в заголовках
     *
     * @param request       HTTP‑запрос, который нужно обработать
     * @param body          тело запроса (массив байт)
     * @return execution    передаёт управление дальше по цепочке перехватчиков
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte @NotNull [] body,
                                        @NotNull ClientHttpRequestExecution execution) throws IOException {
        if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            OAuth2AuthorizedClient authorizedClient =
                    this.oAuth2AuthorizedClientManager.authorize(
                            OAuth2AuthorizeRequest.withClientRegistrationId(this.regId)
                            .principal(this.securityContextHolderStrategy.getContext()
                                    .getAuthentication())
                            .build());
            request.getHeaders().setBearerAuth(
                    authorizedClient != null ? authorizedClient.getAccessToken().getTokenValue() : null);
        }
        return execution.execute(request, body);
    }
}
