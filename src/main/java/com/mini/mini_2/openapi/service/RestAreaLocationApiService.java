package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
        this.appKey = appKey;
        this.openApiRestAreaLocationUrl = openApiRestAreaUrl;
        this.restAreaWebClient = builder
                .build();
    }
    
    public RestAreaLocationApiResponseDTO location(RestAreaLocationApiRequestDTO request) {
        return locationReactive(request).block();
    }

    public reactor.core.publisher.Mono<RestAreaLocationApiResponseDTO> locationReactive(RestAreaLocationApiRequestDTO request) {
        final int pageSize = 99; 

        java.util.function.IntFunction<String> urlForPage = (pageNo) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(openApiRestAreaLocationUrl)
              .append("?type=json")
              .append("&key=").append(appKey)
              .append("&numOfRows=").append(pageSize)
              .append("&pageNo=").append(pageNo);
            if (request.getStdRestCd() != null && !request.getStdRestCd().isBlank()) {
                sb.append("&stdRestCd=").append(request.getStdRestCd());
            }
            return sb.toString();
        };

        reactor.core.publisher.Mono<RestAreaLocationApiResponseDTO> firstPage = restAreaWebClient.get()
                .uri(urlForPage.apply(1))
                .retrieve()
                .bodyToMono(RestAreaLocationApiResponseDTO.class);

        return firstPage.flatMap(first -> {
            int total = 0;
            try {
                total = Integer.parseInt(first.getCount());
            } catch (Exception e) {
                total = 0;
            }

            if (total <= 0) {
                return reactor.core.publisher.Mono.just(first);
            }

            int totalPages = (int) Math.ceil((double) total / pageSize);

            reactor.core.publisher.Flux<Integer> remainingPages = reactor.core.publisher.Flux.range(2, Math.max(0, totalPages - 1));

            reactor.core.publisher.Flux<RestAreaLocationApiResponseDTO> restFlux = remainingPages
                    .concatMap(page -> restAreaWebClient.get()
                            .uri(urlForPage.apply(page))
                            .retrieve()
                            .bodyToMono(RestAreaLocationApiResponseDTO.class)
                            .onErrorResume(err -> {
                                System.out.println("[RestArea] failed to fetch page " + page + " : " + err.getMessage());
                                return reactor.core.publisher.Mono.empty();
                            })
                    );

            return restFlux.collectList().map(listOfPages -> {
                java.util.List<RestAreaLocationApiResponseDTO.RestAreaLocationDTO> merged = new java.util.ArrayList<>();
                if (first.getList() != null) merged.addAll(first.getList());
                for (RestAreaLocationApiResponseDTO p : listOfPages) {
                    if (p.getList() != null) merged.addAll(p.getList());
                }
                RestAreaLocationApiResponseDTO out = new RestAreaLocationApiResponseDTO();
                out.setList(merged);
                out.setCode(first.getCode());
                out.setMessage(first.getMessage());
                out.setCount(String.valueOf(merged.size()));
                out.setPageNo("1");
                out.setNumOfRows(String.valueOf(pageSize));
                out.setPageSize(String.valueOf(merged.size()));
                return out;
            });
        });
    }
    
    // public RestAreaLocationApiResponseDTO location(RestAreaLocationApiRequestDTO request) {
    //     String url = openApiRestAreaLocationUrl +
    //                 "?type=json" +
    //                 "&key=" + appKey +
    //                 "&numOfRows=99";
    //     if(request.getStdRestCd() != "") { 
    //         url = url + "&stdRestCd=" + request.getStdRestCd();
    //     }
    //     System.out.println("[WEB CLIENT] GET URL " + url);
    //     return restAreaWebClient.get()
    //             .uri(url)
    //             .retrieve()
    //             .bodyToMono(RestAreaLocationApiResponseDTO.class)
    //             .block();
    // }

}
