package org.chern.customer.repo;

import org.chern.customer.entity.ChosenProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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

    /**
     * Удаление товара из списка избранных в памяти приложения.
     * @param productId
     * @return Mono.empty()
     */
    @Override
    public Mono<Void> deleteByProductId(int productId) {
        this.chosenProductlist.removeIf(chosenProduct -> chosenProduct.getProductId() == productId);
        return Mono.empty();
    }

    /**
     * Ищет в хранилище товар по метке "В избранном" и
     * отображает информацию на UI в виде кнопки button.
     *
     * @param productId  id искомого товара
     * @return           продукт в избранном либо null
     */
    @Override
    public Mono<ChosenProduct> findByProductId(int productId) {
        return Flux.fromIterable(this.chosenProductlist)
            .filter(chosenProduct -> chosenProduct.getProductId() == productId)
            .singleOrEmpty(); // <- аналог findFirst()
    }
}
