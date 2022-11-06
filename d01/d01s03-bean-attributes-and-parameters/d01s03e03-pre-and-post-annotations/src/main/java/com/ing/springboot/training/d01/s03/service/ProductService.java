package com.ing.springboot.training.d01.s03.service;

import com.ing.springboot.training.d01.s03.repository.ProductRepository;

import javax.annotation.PreDestroy;

/**
 * A simple product service, which uses a {@link ProductRepository} as a collaborator
 *
 * @author bogdan.solga
 */
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void displayProducts() {
        productRepository.displayProducts();
    }

    @PreDestroy
    public void closeResources() {
        System.out.println("Closing the used resources, by the PreDestroy annotation...");
    }
}
