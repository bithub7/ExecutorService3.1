package org.spring.module.controller;

import org.spring.module.model.Quote;
import org.spring.module.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InformationController {

    private QuoteRepository quoteRepository;

    @Autowired
    public InformationController(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/getinf")
    public String index() {
        //hardcode
        StringBuilder stringBuilder = new StringBuilder();
        List<Quote> quoteArr = quoteRepository.getMostValuableCompanies();
        stringBuilder.append("{\"mostValuableStocks\": [");
        for (Quote quote : quoteArr) {
            stringBuilder.append( " {\"name\": ").append(quote.getCompanyName() + ",").append("\"price\": ").append(quote.getIexRealtimePrice()).append("}").append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("]");
        quoteArr = quoteRepository.getCompaniesHighestPercentageChange();
        stringBuilder.append(",\"maxChangedShares\": [");
        for (Quote quote : quoteArr) {
            stringBuilder.append( " {\"name\": ").append(quote.getCompanyName() + ",").append("\"priceDifference\": ").append(quote.getIexRealtimePrice()).append("}").append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("]}");
        return new String(stringBuilder);
    }
}