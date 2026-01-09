package org.chern.customer.controller;

import lombok.RequiredArgsConstructor;
import org.chern.customer.client.ProductsClient;
import org.chern.customer.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * Отдельный контроллер для работы с
 * единичным товаром.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;

    /**
     * Неблокирующий поиск товара по идентификатору в каталоге.
     * @param id  Идентификатор товара в каталоге
     * @return    Mono<Product>
     */
    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> loadProduct(@PathVariable("productId") final int id) {
        return this.productsClient.findProduct(id);
    }

    /**
     * Получение страницы конкретного товара из каталога.
     * @return  шаблон страницы товара
     */
    @GetMapping
    public String getProductPage() {
        return "customer/products/product";
    }
}
