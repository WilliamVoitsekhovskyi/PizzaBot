package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class Application {

    @Autowired
    OrderReceiver orderReceiver = new OrderReceiver();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            orderReceiver.orderPizza();
        };
    }


}