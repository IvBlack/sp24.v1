package org.chern.manager.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chern.manager.client.BadRequestException;
import org.chern.manager.client.ProductsRestClient;
import org.chern.manager.controller.payload.NewProductPayload;
import org.chern.manager.entity.Product;
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

    //Сервис заменен на RestClient с аналогичными методами по работе с сущностью.
    private final ProductsRestClient productsRestClient;

    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productsRestClient.findAllProducts());
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProduct() {
        return "catalogue/products/new_product";
    }

    /*
        Задача - получить данные из формы на фронте, на их основе создать новый продукт.
        Данные опишем на основе объекта payload.NewProductPayload.

        Удалена валидация из manager-app, перенесена в  catalogue-api.
    */
    @PostMapping("create")
    public String createProduct(NewProductPayload payload,
                                Model model) {
        try {
            Product product = this.productsRestClient.createProduct(payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/new_product";
        }
    }
}
