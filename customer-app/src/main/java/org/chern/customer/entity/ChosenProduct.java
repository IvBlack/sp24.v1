package org.chern.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Сущность для избранного товара.
 * Товар можно добавить в избранное на UI кликом по кнопке.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChosenProduct {

    private UUID uuid;

    private int productId;
}
