package org.chern.manager.repo;

import org.chern.manager.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Product save(Product product);
}
