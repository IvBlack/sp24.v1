package org.chern.manager.config;

import org.chern.manager.client.RestClientProductsRestClient;
import org.chern.manager.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    /**
     * Здесь REST-клиент собран для доступа к API каталога.
     * В цепочку встроен пользовательский перехватчик.
     * clientRegistrationRepository — репозиторий регистраций КЛИЕНТОВ, описанных в файле .yaml-config.
     * authorizedClientRepository — информация об авторизованных пользователях между запросами.
     *
     * @param catalogueBaseUri                базовый URL сервиса каталога
     * @param clientRegistrationRepository
     * @param authorizedClientRepository
     * @param regId                           ID регистрации клиента в провайдере OAuth2.0 (keycloak)
     */
    @Bean
    public RestClientProductsRestClient productsRestClient(
            @Value("${retail.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${retail.services.catalogue.registration-id: keycloak}") String regId
            )
    {
        return new RestClientProductsRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository,
                                        authorizedClientRepository), regId))
                .build());
    }
}
