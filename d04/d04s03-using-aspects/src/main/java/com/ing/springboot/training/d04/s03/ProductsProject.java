package com.ing.springboot.training.d04.s03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsProject {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ProductsProject.class);
        application.setAdditionalProfiles(RunProfiles.DEV);
        application.run(args);
    }
}
