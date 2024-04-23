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
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                           @Valid @RequestBody UpdateProductPayload payload,
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

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        this.productService.deleteProduct(productId);
        return ResponseEntity.noContent()
                .build();
    }

    //метод отработает, если сущность не будет найдена при операциях CRUD
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException ex, Locale locale) {
        //собираем ответ с нужным статусом и телом, локализацией, интернационализацией
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource
                                .getMessage(ex.getMessage(), new Object[0],
                                        ex.getMessage(), locale))
                ));
    }
}
