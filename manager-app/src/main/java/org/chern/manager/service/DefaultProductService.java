package org.chern.manager.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chern.manager.entity.Product;
import org.chern.manager.repo.InMemoryProductRepository;
import org.chern.manager.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
