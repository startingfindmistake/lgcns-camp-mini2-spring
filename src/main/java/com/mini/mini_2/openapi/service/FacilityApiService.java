package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.openapi.domain.dto.FacilityApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.FacilityApiResponseDTO;

@Service
public class FacilityApiService {
    private final WebClient facilityWebClient;
    private final String openApiFacilityUrl;
    private final String appKey;
    
    public FacilityApiService(WebClient.Builder builder,
                        @Value("${OPENAPI_KEY}") String appKey,
                        @Value("${OPENAPI_FACILITY_URL}") String openApiFacilityUrl) {
        this.appKey = appKey;
        this.openApiFacilityUrl = openApiFacilityUrl;
        this.facilityWebClient = builder
                .build();
    }
    
    public FacilityApiResponseDTO facility(FacilityApiRequestDTO request) {
        String url = openApiFacilityUrl +
                "?type=json" +
                "&key=" + appKey;
        if (request.getStdRestCd() != "") {
            url = url + "&stdRestCd=" + request.getStdRestCd();
        }
        System.out.println("[WEB CLIENT] GET URL " + url);
        
        return facilityWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(FacilityApiResponseDTO.class)
                .block();
    }
}
