package com.ing.spring.boot.training.d03.s02.service;

import com.ing.spring.boot.training.d03.s02.entity.Product;
import com.ing.spring.boot.training.d03.s02.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductService(final ProductRepository productRepository,
                          final MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
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

    @PostConstruct
    public void display() {
        final Set<String> collectionNames = mongoTemplate.getCollectionNames();
        collectionNames.forEach(System.out::println);
    }
}
