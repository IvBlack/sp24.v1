package org.chern.customer.service;

import org.chern.customer.entity.ChosenProduct;
import reactor.core.publisher.Mono;

public interface ChosenProductService {

    public Mono<ChosenProduct> addProductToChosen(int productId);

    public Mono<Void> removeProductFromChosen(int productId);
}
