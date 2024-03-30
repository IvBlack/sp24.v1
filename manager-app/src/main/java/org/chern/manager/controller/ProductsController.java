package org.chern.manager.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chern.manager.controller.payload.NewProductPayload;
import org.chern.manager.entity.Product;
import org.chern.manager.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Данные опишем на основе объекта payload.NewProductPayload
    */
    @PostMapping("create")
    public String createProduct(NewProductPayload payload) {
        Product product = productService.createProduct(payload.title(), payload.details());
        return  "redirect:/catalogue/products/%d".formatted(product.getId());
    }
}
