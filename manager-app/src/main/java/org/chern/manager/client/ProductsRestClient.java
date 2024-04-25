package org.chern.manager.client;

import org.chern.manager.entity.Product;

import java.util.List;
import java.util.Optional;

/*
Клиент создан для реализации логики работы с товарами со стороны клиентского приложения менеджера,
взамен ушедшим в catalogue сервисам.
Клиент реализован на базе RestClient, но его можно реализовать так же с помощью
RestTemplate Spring.
* */
public interface ProductsRestClient {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);
    Optional<Product> findProduct(int productId);

    void updateProduct(int productId, String title, String details);

    void deleteProduct(int productId);
}
