package org.chern.customer.repo;

import org.chern.customer.entity.ChosenProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public interface ChosenProductRepository {

    Mono<ChosenProduct> save(ChosenProduct chosenProduct);

    Mono<Void> deleteByProductId(int productId);
}
