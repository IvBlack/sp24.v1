package org.chern.customer.client;

import lombok.RequiredArgsConstructor;
import org.chern.customer.entity.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Логика запроса списка товаров через Web Client.
 *
 * Принцип webClient тот же: обратиться на end-point нужным методом,
 * retrieve() - механизм для отправки и порлучения запроса,
 * преобразовать тело ответа в объект Flux<>.
 * С тем отличием, что все происходит в асинхронном и неблокирующем режиме.
 */
@RequiredArgsConstructor
public class ProductsWebClient implements ProductsClient {

    private final WebClient webClient;

    /**
     * Запрос коллекции товаров из каталога
     * по фильтру в асинхронном режиме.
     *
     * @param filter    Фраза поиска товара либо ее часть
     * @return          Flux<Product>
     */
    @Override
    public Flux<Product> findAllProducts(final String filter) {
        return this.webClient
                .get()
                .uri("/catalogue-api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class);
    }


    /**
     * Запрос единичного товара из каталога
     * по id в асинхронном режиме.
     *
     * @param id  Идентификатор товара в каталоге
     * @return    Product
     */
    @Override
    public Mono<Product> findProduct(final int id) {
        return this.webClient
                .get()
                .uri("/catalogue-api/products/productId")
                .retrieve()
                .bodyToMono(Product.class);
    }
}
