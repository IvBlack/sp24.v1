package org.chern.customer.controller;


import lombok.RequiredArgsConstructor;
import org.chern.customer.client.ProductsClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products")
public class ProductsController {

    private final ProductsClient productsClient;

    /**
     * Возвращает Mono со строкой имени шаблона представления, предварительно загрузив список продуктов
     * и добавив его в модель.
     *
     *   Выполняется запрос к productsClient для получения потока продуктов с указанным фильтром.
     *   Элементы потока собираются в список с помощью {@link Flux#collectList()}.
     *   Собранный список добавляется в модель через {@link Model#addAttribute(String, Object)}.
     *   Возвращается имя шаблона представления ("customer/products/list").
     *
     * Важно: Используется {@link Mono#doOnNext(Consumer)} вместо {@link Mono#subscribe()},
     * чтобы избежать асинхронного выполнения подписки, которое может произойти после возврата метода.
     * {@code doOnNext} гарантирует, что добавление в модель произойдёт в рамках текущего реактивного конвейера.
     *
     * @param model  модель представления Spring MVC, куда будет добавлен список продуктов под ключом "products"
     * @param filter необязательный параметр запроса для фильтрации продуктов; может быть null
     * @return Mono, содержащий строку имени шаблона представления после успешного добавления списка продуктов в модель
     * @see ProductsClient#findAllProducts(String)
     * @see Model#addAttribute(String, Object)
     */
    @GetMapping("list")
    public Mono<String> getProductsListPage(Model model, @RequestParam(name = "filter", required = false) String filter) {

        return this.productsClient.findAllProducts(filter)
            .collectList()
            // .subscribe(products -> model.addAttribute("products", products))
            .doOnNext(products -> model.addAttribute("products", products))
            .thenReturn("customer/products/list");
    }
}
