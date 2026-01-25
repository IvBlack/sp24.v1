package org.chern.customer.controller;

import lombok.RequiredArgsConstructor;
import org.chern.customer.client.ProductsClient;
import org.chern.customer.entity.Product;
import org.chern.customer.service.ChosenProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Отдельный контроллер для работы с единичным товаром.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;

    private final ChosenProductService chosenProductService;

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

    /**
     * Контроллер добавляет товар в список избранных,
     * перенаправляет исполнение на страницу товара в едином стриме.
     * @param monoProduct  модель товара
     * @return             страница конкретного товара
     */
    @PostMapping("put-to-chosen")
    public Mono<String> putProductToChosen(@ModelAttribute("product") Mono<Product> monoProduct) {
        return monoProduct
                .map(Product::id)
                .flatMap(id -> this.chosenProductService.addProductToChosen(id) // <- объединение стримов
                    .thenReturn("redirect:/customer/products/%d".formatted(id)));
    }

    /**
     * Контроллер удаляет товар из списка избранных,
     * перенаправляет исполнение на страницу товара в едином стриме.
     * @param monoProduct  модель товара
     * @return             страница конкретного товара
     */
    @DeleteMapping("delete-from-chosen")
    public Mono<String> removeProductFromChosen(@ModelAttribute("product") Mono<Product> monoProduct) {
        return monoProduct
                .map(Product::id)
                .flatMap(id -> this.chosenProductService.removeProductFromChosen(id)
                    .thenReturn("redirect:/customer/products/%d".formatted(id)));
    }
}
