package org.chern.customer.service;

import org.chern.customer.entity.ChosenProduct;
import org.chern.customer.entity.Product;
import reactor.core.publisher.Mono;

public interface ChosenProductService {

    public Mono<ChosenProduct> addProductToChosen(int productId);

    public Mono<Void> removeProductFromChosen(int productId);

    public Mono<ChosenProduct> findChosenProductByProduct(int productId);
}
