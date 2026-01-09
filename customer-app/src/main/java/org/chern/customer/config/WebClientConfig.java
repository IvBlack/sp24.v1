package org.chern.customer.config;

import org.chern.customer.client.ProductsWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * Регистрация Web Client в приложении.
     *
     * @param catalogueBaseUrl
     * @return ProductsWebClient
     */
    @Bean
    public ProductsWebClient webClientProductsClient(
            @Value("{retail.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl
    ) {
        return new ProductsWebClient(WebClient.builder()
                .baseUrl(catalogueBaseUrl)
                .build());
    }
}
