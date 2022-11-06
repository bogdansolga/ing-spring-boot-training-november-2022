package com.ing.springboot.training.d03.s01.repository;

import com.ing.spring.training.jpa.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A simple Spring Data {@link CrudRepository} for the {@link Product} entity
 *
 * @author bogdan.solga
 */
@Repository
@SuppressWarnings("unused")
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<List<Product>> findByName(final String name);

    @Query(value =  "SELECT product " +
                    "FROM Product product " +
                    "WHERE product.name LIKE :name"
    )
    Optional<List<Product>> findProductsWhichIncludeName(@Param(value = "name") final String name);

    Optional<Product> findById(int id);

    Optional<List<Product>> findAll(Pageable pageable);
}
