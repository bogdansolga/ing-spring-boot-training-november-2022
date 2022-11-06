package com.ing.springboot.training.d02.s05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A small Spring Boot demo used to demo the usage of the @{@link org.springframework.jdbc.core.JdbcTemplate} class
 *
 * @author bogdan.solga
 */
@SpringBootApplication
public class JdbcTemplateUsageDemo {

    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateUsageDemo.class, args);
    }
}
