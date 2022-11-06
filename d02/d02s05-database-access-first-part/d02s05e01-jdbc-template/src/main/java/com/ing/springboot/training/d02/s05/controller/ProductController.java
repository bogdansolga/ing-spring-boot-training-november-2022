package com.ing.springboot.training.d02.s05.controller;

import com.ing.springboot.training.d02.s05.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{minPrice}")
    public String getProductsWithMinPrice(@PathVariable final int minPrice) {
        final Integer productsWithMinPrice = productService.getProductsWithMinPrice(minPrice);
        return "There are " + productsWithMinPrice + " products with the price bigger than " + minPrice;
    }
}
