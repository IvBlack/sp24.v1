package org.chern.manager.controller.payload;

/*
    Имена параметров соответствуют полностью именам атрибутов
    в тегах на странице создания нового товара.
*/
public record NewProductPayload(String title, String details) {
}
