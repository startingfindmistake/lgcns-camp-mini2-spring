package com.mini.mini_2.tmapapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.tmapapi.domain.dto.RoutePoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiResponseDTO;

@Service
public class RoutePoiService {
    
    private final WebClient poiWebClient;
    private final String tmapRoutePoiUrl;
    private final String appKey;

    public RoutePoiService(WebClient.Builder builder,
                        @Value("${TMAPAPI_KEY}") String appKey,
                        @Value("${TMAPAPI_ROUTE_POI_URL}") String tmapRoutePoiUrl) {
        this.appKey = appKey;
        this.tmapRoutePoiUrl = tmapRoutePoiUrl;
        this.poiWebClient = builder
                .defaultHeader("appKey", this.appKey)
                .build();
    }
    
    public RoutePoiResponseDTO poi(RoutePoiRequestDTO request) {
        String url = tmapRoutePoiUrl + "?version=1";
        System.out.println("[WEB CLIENT] POST URL " + url);
        RoutePoiResponseDTO result = poiWebClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RoutePoiResponseDTO.class)
                .block();
                
        System.out.println("[POI SERVICE] result : " + result);
                
        return result;
    }
}
