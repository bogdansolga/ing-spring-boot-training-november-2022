package com.ing.springboot.training.d01.s04.service;

import com.ing.springboot.training.d01.s04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductProcessor {

    @Autowired
    private Optional<ProductRepository> productRepository;
}
