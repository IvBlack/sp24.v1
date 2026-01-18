package org.chern.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Сущность,
 * представляющая отзыв покупателей о конкретном товаре
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductComment {

    private int productId;

    private UUID uuid;

    private String review;

    private int rating;
}
