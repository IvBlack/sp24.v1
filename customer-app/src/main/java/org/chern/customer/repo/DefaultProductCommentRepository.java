package org.chern.customer.repo;

import org.chern.customer.entity.ProductComment;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Дефолтный репозиторий для работы с комментариями к товару.
 * Могут использоваться разные технологии для обращения к хранилищу.
 * Для этого нужно переопределить ProductCommentRepository со своей реализацией.
 */
@Repository
public class DefaultProductCommentRepository implements ProductCommentRepository {

    @Override
    public Mono<ProductComment> save(ProductComment productComment) {
        return null;
    }

    @Override
    public Flux<ProductComment> findAllCommentByproductId(int productId) {
        return null;
    }
}
