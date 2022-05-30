package org.spring.module.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.spring.module.dto.CompanyDto;
import org.spring.module.dto.QuoteDto;
import org.spring.module.model.Quote;
import org.spring.module.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Data
@Component
@RequiredArgsConstructor
public class IexApiClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private QuoteRepository quoteRepository;
    @Value("${iex.api.host}")
    private String apiHost;
    @Value("${iex.api.token}")
    private String apiToken;
    private List<Quote> quoteList = new ArrayList<>();

    public CompletableFuture<Void> getCompanyDetails(String companyIdentifier) {
        String urlComInf = apiHost + "stock/" + companyIdentifier + "/quote?token=" + apiToken;
        return CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(urlComInf, QuoteDto.class)).thenAccept(quoteDto -> quoteRepository.save(quoteDto.toQuote()));
    }

    public List<String> getCompanyNames() {
        String urlCompanyNames = apiHost + "ref-data/symbols?token=" + apiToken;
        System.out.println(urlCompanyNames);
        ResponseEntity<CompanyDto[]> response = restTemplate.getForEntity(urlCompanyNames, CompanyDto[].class);
        List<String> companyNameArr = new ArrayList<>();
        for(CompanyDto company : response.getBody()){
            companyNameArr.add(company.getSymbol());
        }
        //лист компаний ограничил до 200 т.к. весь массив имен
        // мой пк не успевает обработать за 5 секунд
        companyNameArr = companyNameArr.subList(0, 200);
        return companyNameArr;
    }
}
