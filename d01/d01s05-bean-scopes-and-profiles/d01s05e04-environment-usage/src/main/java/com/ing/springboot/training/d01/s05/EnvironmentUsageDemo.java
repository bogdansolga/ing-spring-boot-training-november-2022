package com.ing.springboot.training.d01.s05;

import com.ing.springboot.training.d01.s05.config.BeanProfilesDevConfig;
import com.ing.springboot.training.d01.s05.config.BeanProfilesProdConfig;
import com.ing.springboot.training.d01.s05.service.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * An example of how to use a Spring {@link org.springframework.core.env.Environment}
 */
public class EnvironmentUsageDemo {

    private static final String PROFILES_ACTIVATION_PROPERTY = "spring.profiles.active";

    public static void main(String[] args) {
        System.setProperty(PROFILES_ACTIVATION_PROPERTY, RunProfiles.DEV);

        final ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(BeanProfilesDevConfig.class, BeanProfilesProdConfig.class);

        final ProductService productService = applicationContext.getBean(ProductService.class);
        productService.displayProducts();
    }
}
