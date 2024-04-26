package org.chern.catalogue.service;

import org.chern.catalogue.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllProducts();

    Product createProduct(String title, String details);

    Optional<Product> findProductById(int productId);

    void updateProduct(Integer id, String title, String details);

    void deleteProduct(Integer id);
}
