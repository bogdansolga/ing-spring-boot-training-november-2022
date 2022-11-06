package com.ing.springboot.training.d01.s05;

import com.ing.springboot.training.d01.s05.config.BeanProfilesConfig;
import com.ing.springboot.training.d01.s05.config.ProfileEnabledConfig;
import com.ing.springboot.training.d01.s05.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A simple demo of a Spring project to demo the usage of {@link org.springframework.context.annotation.Bean}
 * activated pragmatically per {@link org.springframework.context.annotation.Profile}s
 *
 * @author bogdan.solga
 */
public class BeanCommandLineProfilesDemo {

    public static void main(String[] args) {
        // the profiles specifying parameter ('-Dspring.profiles.active') is set as a command-line argument
        final AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(BeanProfilesConfig.class, ProfileEnabledConfig.class);

        final ProductService productService = applicationContext.getBean(ProductService.class);
        productService.displayProducts();
    }
}
