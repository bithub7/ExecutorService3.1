package org.spring.module;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.spring.module.model.Company;
import org.spring.module.model.Quote;
import org.spring.module.service.ExchangeParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Main {

    private static ExchangeParserService exchangeParserService = new ExchangeParserService();

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
        exchangeParserService.runParser();

    }
}