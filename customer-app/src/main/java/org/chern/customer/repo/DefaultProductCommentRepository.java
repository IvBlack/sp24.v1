package org.chern.customer.repo;

import org.chern.customer.entity.ProductComment;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Дефолтный репозиторий для работы с комментариями к товару.
 * Могут использоваться разные технологии для обращения к хранилищу.
 * Для этого нужно переопределить ProductCommentRepository со своей реализацией.
 */
@Repository
public class DefaultProductCommentRepository implements ProductCommentRepository {

    private final List<ProductComment> productCommentList = Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<ProductComment> save(ProductComment productComment) {
        this.productCommentList.add(productComment);
        return Mono.just(productComment);
    }

    /**
     * Фильтрация комментариев, взятых из хранилища по идентификатору продукта.
     * @param productId  идентификатор продукта
     * @return           Flux<ProductComment>
     */
    @Override
    public Flux<ProductComment> findAllCommentByproductId(int productId) {
        return Flux.fromIterable(this.productCommentList)
                .filter(productComment -> productComment.getProductId() == productId);
    }
}
