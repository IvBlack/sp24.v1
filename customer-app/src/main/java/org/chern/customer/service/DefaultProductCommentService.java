package org.chern.customer.service;

import lombok.RequiredArgsConstructor;
import org.chern.customer.entity.ProductComment;
import org.chern.customer.repo.ProductCommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 *  * Дефолтный сервис-слой для работы с комментариями к товару.
 *  * Могут использоваться разные технологии для работы с сущностью комментария.
 *  * Для этого нужно переопределить ProductCommentService со своей реализацией.
 */
@Service
@RequiredArgsConstructor
public class DefaultProductCommentService implements ProductCommentService {

    private final ProductCommentRepository productCommentRepository;

    @Override
    public Mono<ProductComment> createProductComment(int productId, String review, int rating) {
        return this.productCommentRepository
                .save(new ProductComment(UUID.randomUUID(), productId, review, rating));
    }

    @Override
    public Flux<ProductComment> findCommentByProductId(int productId) {
        return this.productCommentRepository.findAllCommentByproductId(productId);
    }
}
