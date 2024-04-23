package org.chern.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.chern.manager.client.ProductsRestClient;
import org.chern.manager.controller.payload.UpdateProductPayload;
import org.chern.manager.entity.Product;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;


/*Контроллер предназначен для работы с конкретным продуктом*/
@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRestClient productsRestClient;
    private final MessageSource messageSource;

    /*
        Решает проблему задвоения кода в методах работы с продуктом
        путем выноса метода работы с продуктом во вне
    */
    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productsRestClient.findProduct(productId).orElseThrow(()
                -> new NoSuchElementException("{catalogue.errors.product.not_found}"));
    }

    //получить конкретный товар по его id
    @GetMapping
    public String getProduct() {return "catalogue/products/product";}

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
        this.productsRestClient.updateProduct(product.id(), payload.title(), payload.details());
        return "redirect:/catalogue/products/%d".formatted(product.id());
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productsRestClient.deleteProduct(product.id());
        return "redirect:/catalogue/products/list";
    }

    //метод отлавливает ошибки не найденной по запросу сущности
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException ex, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(ex.getMessage(), new Object[0], ex.getMessage(), locale));
        return "errors/404";
    }
}
