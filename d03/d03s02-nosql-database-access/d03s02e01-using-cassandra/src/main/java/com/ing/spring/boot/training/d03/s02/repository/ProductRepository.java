package com.ing.spring.boot.training.d03.s02.repository;

import com.ing.spring.boot.training.d03.s02.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
