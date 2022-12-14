package com.ing.springboot.training.d01.s03.config;

import com.ing.springboot.training.d01.s03.repository.ProductRepository;
import com.ing.springboot.training.d01.s03.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A simple Spring configuration, which wires two simple {@link Bean}s
 *
 * @author bogdan.solga
 */
@Configuration
public class PrePostAnnotationsConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    public ProductService productService() {
        return new ProductService(productRepository());
    }
}
