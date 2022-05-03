package org.spring.module.service;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.spring.module.Main;
import org.spring.module.model.Company;
import org.spring.module.model.Quote;
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
public class ExchangeParserService {

    private Gson gson = new Gson();
    private List<Quote> quoteArr = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();
    private List<Company> companyArr = new ArrayList<>();
    private String urlCompanyNames = "https://sandbox.iexapis.com/stable/ref-data/symbols?token=Tpk_ee567917a6b640bb8602834c9d30e571";

//    public static void main(String[] args) {
//        SpringApplication.run(ExchangeParserService.class, args);
//        new ExchangeParserService().runTest();
//    }

    public void runTest(){
        getCompanyNames();
            ExecutorService pool = Executors.newFixedThreadPool(600);
            for (Company company : companyArr) {
                pool.execute(() -> getInformationCompany(company.getSymbol()));
            }
            pool.shutdown();
            try {
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

    }

    public void runParser() {
        getCompanyNames();
        while (true) {
            ExecutorService pool = Executors.newFixedThreadPool(600);
            for (Company company : companyArr) {
                pool.execute(() -> getInformationCompany(company.getSymbol()));
            }
            pool.shutdown();
            try {
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Collections.sort(quoteArr, (a, b) -> a.getIexRealtimePrice() <
                                                 b.getIexRealtimePrice() ? -1 :
                                                 a.getIexRealtimePrice() ==
                                                 b.getIexRealtimePrice() ? 0 : 1);

            System.out.println();
            System.out.println("Самые дорогие акции : ");
            for (int i = 0; i < 5; i++) {
                System.out.println(quoteArr.get(i).getCompanyName() + " : " + quoteArr.get(i).getIexRealtimePrice());
            }

            Collections.sort(quoteArr, (a, b) -> Math.abs(a.getIexRealtimePrice() - a.getLatestPrice()) <
                                                 Math.abs(b.getIexRealtimePrice() - b.getLatestPrice()) ? -1 :
                                                 Math.abs(a.getIexRealtimePrice() - a.getLatestPrice()) ==
                                                 Math.abs(b.getIexRealtimePrice() - b.getLatestPrice()) ? 0 : 1);
            System.out.println();
            System.out.println("Максимальное измиение : ");
            for (int i = 0; i < 5; i++) {
                System.out.println(quoteArr.get(i).getCompanyName() + " : " + quoteArr.get(i).getIexRealtimePrice() + " " + (quoteArr.get(i).getIexRealtimePrice() - quoteArr.get(i).getLatestPrice()));
            }
        }
    }

    int i = 0;
    public void getInformationCompany(String symbol){
        try {
            String urlComInf = "https://sandbox.iexapis.com/stable/stock/" + symbol + "/quote?token=Tpk_ee567917a6b640bb8602834c9d30e571";
            Quote quote = restTemplate.getForObject(urlComInf, Quote.class);
            quoteArr.add(quote);
//            System.out.println(quote.getCompanyName() +" "+ i);
            i++;
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void getCompanyNames(){
        ResponseEntity<String> response = restTemplate.getForEntity(urlCompanyNames, String.class);
        String jsonArrCompany = response.getBody();
        JSONArray jsonArray = new JSONArray(jsonArrCompany);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Company company = gson.fromJson(String.valueOf(o), Company.class);
            companyArr.add(company);
        }
    }
}
