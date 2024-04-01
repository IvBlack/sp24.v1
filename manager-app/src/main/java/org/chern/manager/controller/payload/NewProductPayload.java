package org.chern.manager.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
    Имена параметров соответствуют полностью именам атрибутов
    в тегах на странице создания нового товара.
*/
public record NewProductPayload
(
        @NotNull
        @Size(min=3, max=50)
        String title,
        @Size(max=1000)
        String details
) {}
