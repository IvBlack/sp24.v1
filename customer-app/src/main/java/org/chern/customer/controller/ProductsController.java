package org.chern.customer.controller;


import lombok.RequiredArgsConstructor;
import org.chern.customer.client.ProductsClient;
import org.chern.customer.entity.ChosenProduct;
import org.chern.customer.service.ChosenProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("custome/products")
public class ProductsController {

    private final ProductsClient productsClient;
    private final ChosenProductService chosenProductService;

    /**
     * Запрос списка товаров.
     * Агрегация Flux в Mono<List>
     * Размещение списка товаров в модели.
     *
     * (!) Не используем в цепочке ".subscribe(products -> ...)" во избежание неожиданного
     * асинхронного поведения.
     *
     * @param model          Модель для размещения в UI
     * @param filter         Данные фильтрации по выборке из репо
     * @return Mono<String>
     */
    @GetMapping("list")
    public Mono<String> getProductsListPage(final Model model,
                                            @RequestParam(name = "filter", required = false) final String filter) {

        model.addAttribute("filter", filter);

        return this.productsClient.findAllProducts(filter)
            .collectList()
            .doOnNext(products -> model.addAttribute("products", products))
            .thenReturn("customer/products/list");
    }

    @GetMapping("chosens")
    public Mono<String> getChosenProductsPage(final Model model,
                                              @RequestParam(name = "filter", required = false) final String filter) {
        model.addAttribute("filter", filter);
        return this.chosenProductService.findChosenProducts()
            .map(ChosenProduct::getProductId)
            .collectList()
            .flatMap(chosenProducts -> this.productsClient.findAllProducts(filter)
                .filter(product -> chosenProducts.contains(product.id()))
                .collectList()
                .doOnNext(products -> model.addAttribute("products", products))
                .thenReturn("customer/products/chosens"));
    }
}
