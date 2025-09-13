package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.openapi.domain.dto.FoodApiRequest;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO;

@Service
public class FacilityApiService {
    private final WebClient routeWebClient;
    private final String openApiFoodUrl;
    private final String appKey;
    
    public FacilityApiService(WebClient.Builder builder,
                        @Value("${OPENAPI_KEY}") String appKey,
                        @Value("${OPENAPI_FOOD_URL}") String openApiFoodUrl) {
        this.appKey = appKey;
        this.openApiFoodUrl = openApiFoodUrl;
        this.routeWebClient = builder
                .build();
    }
    
    public FoodApiResponseDTO food(FoodApiRequest request) {
        String url = openApiFoodUrl + 
                    "?type=json" + 
                    "&key=" + appKey +
                    "&numOfRows=" + request.getNumOfRows() +
                    "&pageNo=" + request.getPageNo();
        System.out.println("[WEB CLIENT] GET URL " + url);
        
        return routeWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(FoodApiResponseDTO.class)
                .block();
    }
}
