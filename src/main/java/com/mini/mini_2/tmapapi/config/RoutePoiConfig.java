package com.mini.mini_2.tmapapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RoutePoiConfig {
    
    @Value("${TMAPAPI_KEY}")
    private String appKey;

    @Value("${TMAPAPI_ROUTE_POI_URL}")
    private String tmapRoutePoiUrl;
    
    @Bean
    public WebClient poiWebClient(WebClient.Builder builder) {
        return builder.baseUrl(tmapRoutePoiUrl)
                      .defaultHeaders(httpHeaders -> {
                          httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                          httpHeaders.add("appKey", appKey);
                      })
                      .build();
    }
}
