package org.chern.customer.repo;

import org.chern.customer.entity.ProductComment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductCommentRepository {

    Mono<ProductComment> save(ProductComment productComment);

    Flux<ProductComment> findAllCommentByproductId(int productId);
}
