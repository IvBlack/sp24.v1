package org.chern.manager.repo;

import org.chern.manager.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    //лист товаров оптимизирован для многопоточности
    List<Product> products = Collections.synchronizedList(new LinkedList<>());


    /*
        Если здесь вернуть список товаров напрямую,
        то любой компонент может получить к нему доступ.
    */
    @Override
    public List<Product> findAll() {return Collections.unmodifiableList(this.products);}


    /*
    Поскольку хранение данных сейчас - inMemory,
    мысль состоит в получении максимального id товара из текущей коллекции
    и увеличении значения на 1, с последующим добавлением нового товара в коллекцию.
    orElse предусматривает отсутствие товаров в коллекции.
    * */
    @Override
    public Product save(Product product) {
        product.setId(
                this.products.stream()
                        .max(Comparator.comparingInt(Product::getId))
                        .map(Product::getId)
                        .orElse(0)
                            + 1);
        this.products.add(product);
        return product;
    }


    //поиск продукта по переданному id.
    @Override
    public Optional<Product> findById(int productId) {
        return this.products.stream()
                .filter(product -> Objects.equals(productId, product.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.products.removeIf(product -> Objects.equals(id, product.getId()));
    }
}
