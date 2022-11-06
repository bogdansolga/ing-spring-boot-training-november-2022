package com.ing.springboot.training.d01.s05;

import com.ing.springboot.training.d01.s05.config.PropertiesLoadingConfig;
import com.ing.springboot.training.d01.s05.service.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * An example of how to load and use .properties files
 */
public class PropertiesLoadingDemo {

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertiesLoadingConfig.class);

        final ProductService productService = applicationContext.getBean(ProductService.class);
        productService.displayLoadedProperties();
    }
}
