package org.chern.catalogue.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
    Имена параметров соответствуют полностью именам атрибутов
    в тегах на странице создания нового товара.
*/
public record NewProductPayload
(
        @NotNull(message = "{catalogue.products.create.errors.title_is_null}")
        @Size(min=3, max=50, message = "{catalogue.products.create.errors.title_size_is_invalid}")
        String title,
        @Size(max=1000, message = "{catalogue.products.create.errors.details_are_invalid}")
        String details
) {}
