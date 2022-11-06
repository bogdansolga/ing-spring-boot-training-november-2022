package com.ing.springboot.training.d02.s01.custom;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CustomizedSpringBootApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustomizedSpringBootApp.class)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(true)
                .web(WebApplicationType.SERVLET)
                .build()
                .run(args);
    }

    //@Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            System.out.println();
            System.out.println("Insert here any operations that should be executed on the app startup");
        };
    }
}
