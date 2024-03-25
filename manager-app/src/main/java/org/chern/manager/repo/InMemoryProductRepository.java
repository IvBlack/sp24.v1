package org.chern.manager.repo;

import org.chern.manager.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;


/*
    Данные хранятся в памяти,
    а именно рандомно генерируются в конструкторе класса репозитория.
*/
@Repository
public class InMemoryProductRepository implements ProductRepository {

    //лист товаров оптимизирован для многопоточности
    List<Product> products = Collections.synchronizedList(new LinkedList<>());

    InMemoryProductRepository() {
        IntStream.range(1, 4)
                .forEach(i -> this.products.add(
                        new Product(i, "Номер товара %d".formatted(i),
                                "Описание товара %d".formatted(i))
                ));
    }

    /*
        Если здесь вернуть список товаров напрямую,
        то любой компонент может получить к нему доступ.
    */
    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(this.products);
    }
}
