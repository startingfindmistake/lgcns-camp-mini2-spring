package com.mini.mini_2.tmapapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.service.RestAreaService;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiResponseDTO;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiResponseDTO.Poi;

@Service
public class RoutePoiService {
    
    private final WebClient poiWebClient;
    private final String tmapRoutePoiUrl;
    private final String appKey;
    
    @Autowired
    private RestAreaService restAreaService;

    public RoutePoiService(WebClient.Builder builder,
                        @Value("${TMAPAPI_KEY}") String appKey,
                        @Value("${TMAPAPI_ROUTE_POI_URL}") String tmapRoutePoiUrl) {
        this.appKey = appKey;
        this.tmapRoutePoiUrl = tmapRoutePoiUrl;
        this.poiWebClient = builder
                .defaultHeader("appKey", this.appKey)
                .build();
    }
    
    public List<RestAreaResponseDTO> poiOfRoute(RoutePoiRequestDTO request) {
        String url = tmapRoutePoiUrl + "?version=1";
        System.out.println("[WEB CLIENT] POST URL " + url);
        RoutePoiResponseDTO responses = poiWebClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RoutePoiResponseDTO.class)
                .block();
                
        List<RestAreaResponseDTO> results = new ArrayList<>();
        for (Poi poi : responses.getSearchPoiInfo().getPois().getPoi()) {
            String addr = poi.getAddr();
            
            RestAreaResponseDTO restArea = restAreaService.findByAddress(addr);
            
            // System.out.println("[POI OF ROUTE] rest area : " + restArea);
            
            if(restArea != null && !results.contains(restArea)) {
                System.out.println("[results] : " + results);
                System.out.println("[IN FOR] !results.contains(restArea) -> " + !results.contains(restArea));
                results.add(restArea);
            }
        }
                
        // System.out.println("[POI ROUTE SERVICE] result : " + result);
                
        return results;
    }
}
