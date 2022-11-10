package com.ing.spring.boot.training.d03.s02.service;

import com.ing.spring.boot.training.d03.s02.entity.Product;
import com.ing.spring.boot.training.d03.s02.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CassandraTemplate cassandraTemplate;

    @Autowired
    public ProductService(final ProductRepository productRepository,
                          CassandraTemplate cassandraTemplate) {
        this.productRepository = productRepository;
        this.cassandraTemplate = cassandraTemplate;
    }

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        productRepository.save(new Product(1, "Tablet", 300));
        productRepository.save(new Product(2,"Phone", 400));
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long count() {
        return cassandraTemplate.count(Product.class);
    }
}
