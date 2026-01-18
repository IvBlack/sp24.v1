package org.chern.customer.service;

import org.chern.customer.entity.ProductComment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductCommentService {

    Mono<ProductComment> createProductComment(int productId, String review, int rating);

    Flux<ProductComment> findCommentByProductId(int productId);
}
