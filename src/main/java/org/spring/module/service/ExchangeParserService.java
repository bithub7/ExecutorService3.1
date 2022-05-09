package org.spring.module.service;

import com.google.gson.Gson;
import org.spring.module.model.Quote;
import org.spring.module.repository.CompanyRepository;
import org.spring.module.repository.QuoteRepository;
import org.spring.module.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootApplication
public class ExchangeParserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeParserService.class);
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

            LOGGER.info("");
            LOGGER.info("{}", Constants.EXPENSIVE);
            List<Quote> quoteArr = quoteRepository.getMostValuableCompanies();
            for (Quote quote : quoteArr) {
                LOGGER.info("{} : {}",quote.getCompanyName(), quote.getIexRealtimePrice());
            }
            LOGGER.info("");
            LOGGER.info("{}", Constants.MAXIMUM_CHANGED_SHARES);
            quoteArr = quoteRepository.getCompaniesHighestPercentageChange();
            for (Quote quote : quoteArr) {
                LOGGER.info("{} : {}",quote.getCompanyName(), (quote.getIexRealtimePrice() - quote.getLatestPrice()));
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
