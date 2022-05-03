package org.spring.module.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    String symbol;
    String exchange;
    String exchangeSuffix;
    String exchangeName;
    String exchangeSegment;
    String exchangeSegmentName;
    String name;
    String date;
    String type;
    String iexId;
    String region;
    String currency;
    String isEnabled;
    String figi;
    String cik;
    String lei;
}
