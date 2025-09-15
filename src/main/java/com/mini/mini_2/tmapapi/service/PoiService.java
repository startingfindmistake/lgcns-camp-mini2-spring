package com.mini.mini_2.tmapapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.tmapapi.domain.dto.PoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.PoiResponseDTO;

@Service
public class PoiService {
    private final WebClient poiWebClient;
    private final String tmapPoiUrl;
    private final String appKey;
    
    public PoiService(WebClient.Builder builder,
                        @Value("${TMAPAPI_KEY}") String appKey,
                        @Value("${TMAPAPI_POI_URL}") String tmapPoiUrl) {
        this.appKey = appKey;
        this.tmapPoiUrl = tmapPoiUrl;
        this.poiWebClient = builder
                .defaultHeader("appKey", this.appKey)
                .build();
    }
    
    public PoiResponseDTO poi(PoiRequestDTO request) {
        String url = tmapPoiUrl + "?version=1" + 
                     "&searchKeyword=" + request.getSearchKeyword();
                    //  "&appKey" + appKey;
        System.out.println("[WEB CLIENT] GET URL " + url);
        PoiResponseDTO result = poiWebClient.get()
                                            .uri(url)
                                            .retrieve()
                                            .bodyToMono(PoiResponseDTO.class)
                                            .block();
                
        System.out.println("[POI SERVICE] result : " + result);
                
        return result;
    }
    
}
