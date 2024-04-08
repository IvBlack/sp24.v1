package org.chern.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.chern.catalogue.entity.Product;
import org.chern.catalogue.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("catalogue-api/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product findProduct(@PathVariable("productId") int productId) {
        return this.productService.findProductById(productId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    //в случае с rest отдаем то, что получили из модели
    @GetMapping
    public Product getProduct(@ModelAttribute("product") Product product) {
        return product;
    }
}
