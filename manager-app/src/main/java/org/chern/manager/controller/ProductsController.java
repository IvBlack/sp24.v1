package org.chern.manager.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chern.manager.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
