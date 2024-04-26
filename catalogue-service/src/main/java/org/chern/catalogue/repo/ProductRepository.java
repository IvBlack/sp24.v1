package org.chern.catalogue.repo;

import org.chern.catalogue.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/*
    Теперь реализация методов репозитория, как и методы самого репозитория более не нужны.
    Всю реализацию предоставляет CrudRepository через ProductRepository в зависимости от его сигнатуры,
    опираясь на свою внутреннюю логику.
*/
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
