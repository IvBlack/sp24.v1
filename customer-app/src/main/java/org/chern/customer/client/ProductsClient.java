package org.chern.customer.client;

import org.chern.customer.entity.Product;
import reactor.core.publisher.Flux;

public interface ProductsClient {
    Flux<Product> findAllProducts(String filter);
}
