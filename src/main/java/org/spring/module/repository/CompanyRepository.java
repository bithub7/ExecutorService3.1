package org.spring.module.repository;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.spring.module.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private List<Company> companyArr = new ArrayList<>();
    private String urlCompanyNames = "https://sandbox.iexapis.com/stable/ref-data/symbols?token=Tpk_ee567917a6b640bb8602834c9d30e571";
    private RestTemplate restTemplate;
    private Gson gson;

    @Autowired
    public CompanyRepository(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        getCompanyNamesOfApi();
    }

    public List<String> getCompanyNames(){
        List<String> companyNameArr = new ArrayList<>();
        for(Company company : companyArr){
            companyNameArr.add(company.getSymbol());
        }
        return companyNameArr;
    }

    private void getCompanyNamesOfApi(){
        ResponseEntity<String> response = restTemplate.getForEntity(urlCompanyNames, String.class);
        String jsonArrCompany = response.getBody();
        JSONArray jsonArray = new JSONArray(jsonArrCompany);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Company company = gson.fromJson(String.valueOf(o), Company.class);
            companyArr.add(company);
        }
    }
}
