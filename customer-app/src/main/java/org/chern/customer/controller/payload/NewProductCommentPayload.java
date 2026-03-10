package org.chern.customer.controller.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record  NewProductCommentPayload(
        @NotNull(message = "{customer.products.comments.create.errors.rating_not_detected}")
        @Min(value = 1, message = "{customer.products.comments.create.errors.rating_less_min}")
        @Max(value =5, message = "{customer.products.comments.create.errors.rating_more_max}")
        int rating,

        @Size(max = 1000, message = "{customer.products.comments.errors.comment_is_too_big}")
        String comment) {
}
