package com.ebac.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(Main.class, args);

        String port = context.getEnvironment().getProperty("server.port", "8080");

        System.out.println(
                "\n========================================\n" +
                        " 🚀 Biblioteca API levantada correctamente\n" +
                        " 🌐 Puerto: http://localhost:" + port + "\n" +
                        "========================================\n"
        );
    }
}