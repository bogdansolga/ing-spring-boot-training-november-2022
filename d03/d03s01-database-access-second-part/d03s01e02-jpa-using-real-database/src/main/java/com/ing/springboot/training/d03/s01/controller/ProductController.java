package com.ing.springboot.training.d03.s01.controller;

import com.ing.spring.training.jpa.model.Product;
import com.ing.springboot.training.d03.s01.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * A Spring {@link RestController} used to showcase the modeling of a REST controller for CRUD operations
 *
 * @author bogdan.solga
 */
@RestController
@RequestMapping(
        path = "/product"
)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates the referenced {@link Product}
     *
     * @param product the {@link Product} to be created
     *
     * @return a {@link ResponseEntity} with the appropriate {@link HttpStatus}
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        productService.create(product);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Reads the {@link Product} with the specified id
     *
     * @param id the id of the requested {@link Product}
     *
     * @return the serialized {@link Product}
     */
    @GetMapping(path = "/{id}")
    public Product getProduct(@PathVariable final int id) {
        return productService.get(id);
    }

    /**
     * Reads all the existing {@link Product}s
     *
     * @return the serialized {@link Product}s
     */
    @GetMapping
    public Iterable<Product> getAll() {
        return productService.getAll();
    }

    /**
     * Updates the {@link Product} with the specified ID with the details from the referenced {@link Product}
     *
     * @param id the ID of the updated {@link Product}
     * @param product the new {@link Product} details
     *
     * @return a {@link ResponseEntity} with the appropriate {@link HttpStatus}
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable final int id, @RequestBody Product product) {
        productService.update(id, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Deletes the {@link Product} with the specified ID
     *
     * @param id the ID of the deleted {@link Product}
     *
     * @return a {@link ResponseEntity} with the appropriate {@link HttpStatus}
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable final int id) {
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public List<Product> getProductsPage(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return productService.getProductsPage(pageNumber, pageSize);
    }
}
