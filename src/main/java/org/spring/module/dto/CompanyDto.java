package org.spring.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.module.model.Company;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

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

    public Company toCompany(){
        Company company = new Company();
        company.setSymbol(symbol);

        return company;
    }
}
