package org.spring.module;

import org.spring.module.service.ExchangeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static ExchangeParserService exchangeParserService;
    @Autowired
    public Main (ExchangeParserService exchangeParserService){
        this.exchangeParserService = exchangeParserService;
    }

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
        exchangeParserService.runParser();

    }
}