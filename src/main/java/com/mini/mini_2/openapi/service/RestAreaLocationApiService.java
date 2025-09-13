package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiResponseDTO;

@Service
public class RestAreaLocationApiService {
    private final WebClient restAreaWebClient;
    private final String openApiRestAreaLocationUrl;
    private final String appKey;

    public RestAreaLocationApiService(WebClient.Builder builder,
            @Value("${OPENAPI_KEY}") String appKey,
            @Value("${OPENAPI_LOCATION_URL}") String openApiRestAreaUrl) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB
                .build();
        
        this.appKey = appKey;
        this.openApiRestAreaLocationUrl = openApiRestAreaUrl;
        this.restAreaWebClient = builder
                .exchangeStrategies(strategies)
                .build();
    }
    
    public RestAreaLocationApiResponseDTO location(RestAreaLocationApiRequestDTO request) {
        String url = openApiRestAreaLocationUrl +
                    "?type=json" +
                    "&key=" + appKey;
        if(request.getStdRestCd() != "") { 
            url = url + "&stdRestCd=" + request.getStdRestCd();
        }
        System.out.println("[WEB CLIENT] GET URL " + url);
        return restAreaWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(RestAreaLocationApiResponseDTO.class)
                .block();
    }

}
