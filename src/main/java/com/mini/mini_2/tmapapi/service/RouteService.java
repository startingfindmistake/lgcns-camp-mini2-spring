package com.mini.mini_2.tmapapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.tmapapi.domain.dto.RouteRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RouteResponseDTO;

@Service
public class RouteService {

    private final WebClient routeWebClient;
    private final String tmapRouteUrl;
    private final String appKey;

    public RouteService(WebClient.Builder builder,
                        @Value("${TMAPAPI_KEY}") String appKey,
                        @Value("${TMAPAPI_ROUTE_URL}") String tmapRouteUrl) {
        this.appKey = appKey;
        this.tmapRouteUrl = tmapRouteUrl;
        this.routeWebClient = builder
                .defaultHeader("appKey", this.appKey)
                .build();
    }

    public RouteResponseDTO route(RouteRequestDTO request) {
        String url = tmapRouteUrl + "?version=1&format=json";
        System.out.println("[WEB CLIENT] POST URL " + url);
        return routeWebClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RouteResponseDTO.class)
                .block();
    }
}