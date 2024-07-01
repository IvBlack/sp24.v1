package org.chern.customer.client;

import lombok.RequiredArgsConstructor;
import org.chern.customer.entity.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class WebClientProductsClient implements ProductsClient {

    /*
        Принцип webClient тот же: обратиться на end-point нужным методом,
        retrieve() - механизм для отправки и порлучения запроса,
        преобразовать тело ответа в обхект Flux<>.
        С тем отличием, что все происходит в асинхронном и неблокирующем режиме.
    * */
    private final WebClient webClient;

    @Override
    public Flux<Product> findAllProducts(String filter) {
        return this.webClient
                .get()
                .uri("/catalogue-api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
