package org.chern.catalogue.repository;

import org.chern.catalogue.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//
public interface ProductRepository extends CrudRepository<Product, Integer> {

    //repo layer filtration
    @Query(value = "select * from catalogue.t_product е where c_details ilike :filter", nativeQuery = true)
        Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
        }