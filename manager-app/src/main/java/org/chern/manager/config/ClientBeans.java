package org.chern.manager.config;

import org.chern.manager.client.RestClientProductsRestClient;
import org.chern.manager.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    /*
        Here rest-client is assembled to access the catalogue-api.
        Custom interceptor is embedded in the chain.
        clientRegistrationRepository - repo of CLIENT's registrations described in .yaml-config.
        authorizedClientRepository - info about authorized users between requests.
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
