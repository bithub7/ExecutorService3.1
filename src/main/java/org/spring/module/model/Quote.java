package org.spring.module.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Quote {
    private String companyName;
    private double iexRealtimePrice;
    private double latestPrice;

    private List<Double> priceArr = new ArrayList<>();

    public List<Double> addPrice(double price){
        priceArr.add(price);
        return priceArr;
    }

    public List<Double> priceHistory(){
        return priceArr;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        return companyName != null ? companyName.equals(quote.companyName) : quote.companyName == null;
    }

    @Override
    public int hashCode() {
        return companyName != null ? companyName.hashCode() : 0;
    }
}
