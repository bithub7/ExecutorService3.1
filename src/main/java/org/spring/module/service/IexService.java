package org.spring.module.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.module.client.IexApiClient;
import org.spring.module.model.Quote;
import org.spring.module.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class IexService {

    @Value("${iex.api.host}")
    private String apiHost;
    @Value("${iex.api.token}")
    private String apiToken;
    @Autowired
    private QuoteRepository quoteRepository;
    private IexApiClient iexApiClient;
    private List<String> companyNameArr;

    private static final Logger LOGGER = LoggerFactory.getLogger(IexService.class);

    @Autowired
    public IexService(IexApiClient iexApiClient, QuoteRepository quoteRepository){
        this.iexApiClient = iexApiClient;
        this.quoteRepository = quoteRepository;
        companyNameArr = iexApiClient.getCompanyNames();
    }

    @Scheduled(cron="${output.console.quotes.crone}")
    public void informationRecipient() {
        for(String companyIdentifier : companyNameArr){
            iexApiClient.getCompanyDetails(companyIdentifier);
        }
        List<Quote> quoteList = iexApiClient.getQuoteList();
        quoteRepository.saveAll(quoteList);
        iexApiClient.setQuoteList(new ArrayList<>());
    }

    @Scheduled(cron="${get.quotes.iex.cron}")
    public void informationDisplay(){
        LOGGER.info("");
        for(Quote quote : quoteRepository.findFiveMostValuableCompanies()) {
            LOGGER.info(quote.toString());
        }
        LOGGER.info("");
        for(Quote quote : quoteRepository.findFiveCompaniesBiggestChange()){
            LOGGER.info(quote.toString());
        }
    }
}