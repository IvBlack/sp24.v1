package org.chern.manager.controller;

import lombok.RequiredArgsConstructor;
import org.chern.manager.controller.payload.UpdateProductPayload;
import org.chern.manager.entity.Product;
import org.chern.manager.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/*Контроллер предназначен для работы с конкретным продуктом*/
@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*
        Решает проблему задвоения кода в методах работы с продуктом
        путем выноса метода работы с продуктом во вне
    */
    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productService.findProductById(productId).orElseThrow();
    }

    //получить конкретный товар по его id
    @GetMapping
    public String getProduct() {
        return "catalogue/products/product";
    }

    //редакция определенного товара
    @GetMapping("edit")
    public String getProductEditPage() {
        return "catalogue/products/edit";
    }

    /*
    Атрибут модели можно получать прямо в параметрах метода.
    Метод обновляет определенный по id продукт.
    Обновление полей сущности происходит через специально созданный для этого payload.
    * */
    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product, UpdateProductPayload payload) {
        this.productService.updateProduct(product.getId(), payload.title(), payload.details());
        return "redirect:/catalogue/products/%d".formatted(product.getId());
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/catalogue/products/list";
    }
}
