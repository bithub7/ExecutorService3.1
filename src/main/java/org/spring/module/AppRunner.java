package org.spring.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppRunner extends SpringBootServletInitializer {
    public static void main(String[] args){
        SpringApplication.run(AppRunner.class, args);
    }
}
