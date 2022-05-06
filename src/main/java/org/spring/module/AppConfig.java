package org.spring.module;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public Gson gsonBean(){
        return new Gson();
    }

    @Bean
    public RestTemplate restTemplateBean(){
        return new RestTemplate();
    }
}
