package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.openapi.domain.dto.FoodApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO;

@Service
public class FoodApiService {
    private final WebClient foodWebClient;
    private final String openApiFoodUrl;
    private final String appKey;
    
    public FoodApiService(WebClient.Builder builder,
                        @Value("${OPENAPI_KEY}") String appKey,
                        @Value("${OPENAPI_FOOD_URL}") String openApiFoodUrl) {
        this.appKey = appKey;
        this.openApiFoodUrl = openApiFoodUrl;
        this.foodWebClient = builder
                .build();
    }
    
    public FoodApiResponseDTO food(FoodApiRequestDTO request) {
        String url = openApiFoodUrl +
                "?type=json" +
                "&key=" + appKey;
        if (request.getStdRestCd() != "") {
            url = url + "&stdRestCd=" + request.getStdRestCd();
        }
        System.out.println("[WEB CLIENT] GET URL " + url);
        
        return foodWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(FoodApiResponseDTO.class)
                .block();
    }
}
