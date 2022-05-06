package org.spring.module.service;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.spring.module.model.Company;
import org.spring.module.model.Quote;
import org.spring.module.repository.CompanyRepository;
import org.spring.module.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@SpringBootApplication
public class ExchangeParserService {

    private CompanyRepository companyRepository;
    private QuoteRepository quoteRepository;
    private RestTemplate restTemplate;
    private Gson gson;

    @Autowired
    public ExchangeParserService(CompanyRepository companyRepository,
                                 QuoteRepository quoteRepository,
                                 RestTemplate restTemplate,
                                 Gson gson) {
        this.companyRepository = companyRepository;
        this.quoteRepository = quoteRepository;
        this.restTemplate = restTemplate;
        this.gson = gson;
    }


    public void runParser() {
        while (true) {
            int THREADS = 600;
            ExecutorService pool = Executors.newFixedThreadPool(THREADS);
            List<Callable<Object>> tasks = new ArrayList<>();
            try {
                for (String companyName : companyRepository.getCompanyNames()) {
                    tasks.add(() -> getInformationCompany(companyName));
                }
                pool.invokeAll(tasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pool.shutdown();
            }

            System.out.println("Size : " + quoteRepository.size());

            System.out.println();
            System.out.println("Самые дорогие акции : ");
            List<Quote> quoteArr = quoteRepository.getMostValuableCompanies();

            for (Quote quote : quoteArr) {
                System.out.println(quote.getCompanyName() + " : " + quote.getIexRealtimePrice() + " SizePrice : " + quote.getPriceArr().size());
            }

            quoteArr = quoteRepository.getCompaniesHighestPercentageChange();

            System.out.println();
            System.out.println("Максимальное измиение : ");
            for (Quote quote : quoteArr) {
                System.out.println(quote.getCompanyName() + " : " + (quote.getIexRealtimePrice() - quote.getLatestPrice()));
            }
        }
    }

    int i = 0;
    public long getInformationCompany(String symbol){
        try {
            String urlComInf = "https://sandbox.iexapis.com/stable/stock/" + symbol + "/quote?token=Tpk_ee567917a6b640bb8602834c9d30e571";
            Quote quote = restTemplate.getForObject(urlComInf, Quote.class);
            quoteRepository.add(quote);
            i++;
        }catch (Exception e){
            e.getMessage();
        }
        return -1;
    }
}
