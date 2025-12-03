package org.chern.customer.client;

import org.chern.customer.entity.Product;
import reactor.core.publisher.Flux;

public interface ProductsClient {

    /**
     * Метод поиска продукции по фильтру
     * @param filter
     * @return Flux<Product>
     */
    Flux<Product> findAllProducts(String filter);
}
