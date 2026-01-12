package org.chern.customer.repo;

import org.chern.customer.entity.ChosenProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Репозиторий для оперирования сущностью избранных товаров
 * в памяти приложения.
 */
@Repository
public class InMemoryChosenProductRepository implements ChosenProductRepository {

    private final List<ChosenProduct> chosenProductlist = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<ChosenProduct> save(ChosenProduct chosenProduct) {
        this.chosenProductlist.add(chosenProduct);
        return Mono.just(chosenProduct);
    }

    // TODO: realize
    @Override
    public Mono<Void> deleteByProductId(int productId) {
        return null;
    }
}
