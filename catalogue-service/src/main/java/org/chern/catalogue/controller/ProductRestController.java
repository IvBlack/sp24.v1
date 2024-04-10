package org.chern.catalogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chern.catalogue.controller.payload.UpdateProductPayload;
import org.chern.catalogue.entity.Product;
import org.chern.catalogue.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

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


    /*
        Идентификатор товара,
        изменяемые данные в теле запроса и
        результат валидации тела запроса.
        Поскольку клиент сам знает, какие изменения он внес в форму,
        то в блоке ..else {} нет смысла возвращать объект.
    */
    @PatchMapping
    public ResponseEntity<?> updateProduct(@Valid @PathVariable("productId") int productId,
                                           @RequestBody UpdateProductPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent()
                    .build();
        }
    }
}
