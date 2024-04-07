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
    */
    @PostMapping
    public ResponseEntity<?> createproduct(@Valid @RequestBody NewProductPayload payload,
                                                 BindingResult bindingResult,
                                                 UriComponentsBuilder uriComponentsBuilder,
                                                 Locale locale) {
        if(bindingResult.hasErrors()) {
            //структура с кодом, описанием и списком ошибок >>>>>>>>>
            ProblemDetail problemDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                            Objects.requireNonNull(this.messageSource
                                    .getMessage("errors.400.title", new Object[0], "errors.400.title", locale)));
            problemDetail.setProperty("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            //>>>>>>>>>>>>>>>>>>>
            return ResponseEntity.badRequest().body(problemDetail);
        } else {
            Product product = this.productService.createProduct(payload.title(), payload.details());
            return ResponseEntity.created(uriComponentsBuilder
                            .replacePath("catalogue-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }
}
