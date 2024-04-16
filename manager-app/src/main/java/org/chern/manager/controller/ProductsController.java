package org.chern.manager.controller;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chern.manager.controller.payload.NewProductPayload;
import org.chern.manager.entity.Product;
import org.chern.manager.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@Data
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {
    private final ProductService productService;

    /*
        Цель сервиса на данном этапе проста: получить список товаров,
        положить их в модель, модель запушить в шаблон.
    */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProduct() {
        return "catalogue/products/new_product";
    }

    /*
        Задача - получить данные из формы на фронте, на их основе создать новый продукт.
        Данные опишем на основе объекта payload.NewProductPayload.

        @Valid - валидируемые данные, BindingResult - хранилище ошибок,
        ошибки ложатся в модель вместе с payload, дабы юзер не потерял уже введенные данные.
    */
    @PostMapping("create")
    public String createProduct(@Valid NewProductPayload payload,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalogue/products/new_product";
        } else {
            Product product = productService.createProduct(payload.title(), payload.details());
            return  "redirect:/catalogue/products/%d".formatted(product.getId());
        }
    }
}
