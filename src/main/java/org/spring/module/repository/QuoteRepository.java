package org.spring.module.repository;


import org.spring.module.model.Quote;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class QuoteRepository {

    private Set<Quote> quoteSet = new HashSet<>();

    public void add(Quote quote){
        if(quoteSet.contains(quote)){
            Quote tempQuote = getByName(quote.getCompanyName());
            tempQuote.setIexRealtimePrice(quote.getIexRealtimePrice());
            tempQuote.setLatestPrice(quote.getLatestPrice());
            tempQuote.addPrice(quote.getIexRealtimePrice());
            quoteSet.add(quote);
        }else {
            quoteSet.add(quote);
        }
    }

    public Quote getByName(String companyName){
        for(Quote quoteTemp : quoteSet){
            if(quoteTemp.getCompanyName().equals(companyName)) {
                return quoteTemp;
            }
        }
        return null;
    }

    public List<Quote> getMostValuableCompanies(){

        List<Quote> quoteArr = new ArrayList<>(quoteSet);

        Collections.sort(quoteArr, (a, b) -> a.getIexRealtimePrice() >
                                             b.getIexRealtimePrice() ? -1 :
                                             a.getIexRealtimePrice() ==
                                             b.getIexRealtimePrice() ? 0 : 1);

        List<Quote> resultList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resultList.add(quoteArr.get(i));
        }
        return resultList;
    }

    public List<Quote> getCompaniesHighestPercentageChange(){

        List<Quote> quoteArr = new CopyOnWriteArrayList<>(quoteSet);
        for(Quote quote : quoteArr){
            if(quote.priceHistory().size() == 0){
                quoteArr.remove(quote);
            }
        }

        if(quoteArr.isEmpty()){
            return new ArrayList<>();
        }

        Collections.sort(quoteArr, (a, b) -> Math.abs(a.getIexRealtimePrice()) - Math.abs(a.getLatestPrice()) >
                                             Math.abs(b.getIexRealtimePrice()) - Math.abs(b.getLatestPrice()) ? -1 :
                                             Math.abs(a.getIexRealtimePrice()) - Math.abs(a.getLatestPrice()) ==
                                             Math.abs(b.getIexRealtimePrice()) - Math.abs(b.getLatestPrice()) ? 0 : 1);

        List<Quote> resultList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resultList.add(quoteArr.get(i));
        }
        return resultList;
    }

    public int size(){
        return quoteSet.size();
    }
}
