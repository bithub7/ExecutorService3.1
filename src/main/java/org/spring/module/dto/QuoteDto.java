package org.spring.module.dto;

import lombok.*;
import org.spring.module.model.Quote;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {

    String avgTotalVolume;
    String calculationPrice;
    String change;
    String changePercent;
    String close;
    String closeSource;
    String closeTime;
    String companyName;
    String currency;
    String delayedPrice;
    String delayedPriceTime;
    String extendedChange;
    String extendedChangePercent;
    String extendedPrice;
    String extendedPriceTime;
    String high;
    String highSource;
    String highTime;
    String iexAskPrice;
    String iexAskSize;
    String iexBidPrice;
    String iexBidSize;
    String iexClose;
    String iexCloseTime;
    String iexLastUpdated;
    String iexMarketPercent;
    String iexOpen;
    String iexOpenTime;
    Double iexRealtimePrice;
    String iexRealtimeSize;
    String iexVolume;
    String lastTradeTime;
    Double latestPrice;
    String latestSource;
    String latestTime;
    String latestUpdate;
    String latestVolume;
    String low;
    String lowSource;
    String lowTime;
    String marketCap;
    String oddLotDelayedPrice;
    String oddLotDelayedPriceTime;
    String open;
    String openTime;
    String openSource;
    String peRatio;
    String previousClose;
    String previousVolume;
    String primaryExchange;
    String symbol;
    String volume;
    String week52High;
    String week52Low;
    String ytdChange;
    String isUSMarketOpen;

    public Quote toQuote(){
        Quote quote = new Quote();
        quote.setCompanyName(companyName);
        quote.setSymbol(symbol);
        quote.setIexRealtimePrice(iexRealtimePrice);
        quote.setLatestPrice(latestPrice);
        quote.setDate(new Date());
        return quote;
    }
}