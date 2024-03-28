package org.chern.manager.service;

import org.chern.manager.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);
}
