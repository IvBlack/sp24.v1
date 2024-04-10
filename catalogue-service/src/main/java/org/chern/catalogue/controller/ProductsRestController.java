package org.chern.catalogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chern.catalogue.controller.payload.NewProductPayload;
import org.chern.catalogue.entity.Product;
import org.chern.catalogue.service.ProductService;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/*
    Классы данных необходимо создавать индивидуально для каждого модуля приложения,
    т.к. они могут содержать различное кол-во и тип данных в зависимости от
    цели использования. Для упрощения здесь используется один класс данных Product.
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductsRestController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping
    public List<Product> findProducts() {
        return this.productService.findAllProducts();
    }


    /*
        RequestBody подставляет тело запроса в соответствующий аргумент метода
        ResponseEntity комплексно формирует ответ
        ProblemDetail обрабатывает ошибку, возращает описание юзеру

        Выпадаемое исключение BindException отлавливается хэндлером через контроллер
        FallenRequestControllerAdvice, кот.подставляет логику обработки ошибок в места вызова исключения.
    */
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                                 BindingResult bindingResult,
                                                 UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if(bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = this.productService.createProduct(payload.title(), payload.details());
            return ResponseEntity.created(uriComponentsBuilder
                            .replacePath("catalogue-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }
}
