package com.ing.springboot.training.d04.s03.controller;

import com.ing.springboot.training.d04.s03.aop.profiling.ExecutionTimeProfiling;
import com.ing.springboot.training.d04.s03.aop.profiling.MemoryProfiling;
import com.ing.springboot.training.d04.s03.dto.ProductDTO;
import com.ing.springboot.training.d04.s03.service.ProductService;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExecutionTimeProfiling
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable final int id) {
        return productService.get(id);
    }

    @MemoryProfiling
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @ExecutionTimeProfiling
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final int id, @RequestBody ProductDTO productDTO) {
        productService.update(id, productDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final int id) {
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
