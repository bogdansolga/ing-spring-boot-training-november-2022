package com.ing.springboot.training.d01.s01.single.beans;

import org.springframework.context.annotation.Bean;

/**
 * A minimal example of a simple Spring {@link Bean}
 *
 * @author bogdan.solga
 */
public class HelloSpring {

    public void displayWelcomeMessage() {
        System.out.println("Hello, Spring Framework!");
    }
}
