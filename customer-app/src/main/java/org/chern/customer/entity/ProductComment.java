package org.chern.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Сущность, представляющая отзыв покупателей о конкретном товаре
 * и его рейтинге.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductComment {

    private UUID uuid;

    private int productId;

    private String review;

    private int rating;
}
