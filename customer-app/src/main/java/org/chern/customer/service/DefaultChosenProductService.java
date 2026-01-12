package org.chern.customer.service;

import lombok.RequiredArgsConstructor;
import org.chern.customer.entity.ChosenProduct;
import org.chern.customer.repo.ChosenProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Сервис для работы с избранными товарами.
 */
@Service
@RequiredArgsConstructor
public class DefaultChosenProductService implements ChosenProductService {

    private final ChosenProductRepository chosenProductRepository;

    @Override
    public Mono<ChosenProduct> addProductToChosen(int productId) {

        return this.chosenProductRepository.save(new ChosenProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromChosen(int productId) {
        return this.chosenProductRepository.deleteByProductId(productId);
    }
}
