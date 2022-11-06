package com.ing.springboot.training.d04.s02.repository;

import com.ing.spring.training.jpa.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
