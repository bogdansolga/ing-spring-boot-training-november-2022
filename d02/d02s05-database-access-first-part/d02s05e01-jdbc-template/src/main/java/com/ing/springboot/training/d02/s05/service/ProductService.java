package com.ing.springboot.training.d02.s05.service;

import com.ing.springboot.training.d02.s05.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Integer getProductsWithMinPrice(final int minPrice) {
        return productRepository.getProductsWithMinPrice(minPrice);
    }
}
