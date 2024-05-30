package org.chern.manager.security;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import java.io.IOException;

//custom oauth2 interceptor
@RequiredArgsConstructor
public class OAuthClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    //info about current authorized user is here
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    //oauth identifier to get access keys
    private final String regId;

    /*
        Get current security context (thread), SecurityContextHolder is default.
        Set-method is for change type to another securityContextHolderStrategy if needed
    */
    @Setter
    private SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();


    //build authorizedClient with token value in headers
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
