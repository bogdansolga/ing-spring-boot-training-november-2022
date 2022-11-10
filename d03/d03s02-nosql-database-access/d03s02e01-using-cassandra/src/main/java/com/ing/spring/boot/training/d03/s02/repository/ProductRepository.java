package com.ing.spring.boot.training.d03.s02.repository;

import com.ing.spring.boot.training.d03.s02.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<List<Product>> findAllByName(String name);
}
