package com.ing.springboot.training.d02.s05.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getProductsWithMinPrice(final int minPrice) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM product WHERE price > ?", Integer.class, minPrice);
    }
}
