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
        return foodReactive(request).block();
    }

    public reactor.core.publisher.Mono<FoodApiResponseDTO> foodReactive(FoodApiRequestDTO request) {
        final int pageSize = 99; 

        java.util.function.IntFunction<String> urlForPage = (pageNo) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(openApiFoodUrl)
              .append("?type=json")
              .append("&key=").append(appKey)
              .append("&numOfRows=").append(pageSize)
              .append("&pageNo=").append(pageNo);
            if (request.getStdRestCd() != null && !request.getStdRestCd().isBlank()) {
                sb.append("&stdRestCd=").append(request.getStdRestCd());
            }
            return sb.toString();
        };

        reactor.core.publisher.Mono<FoodApiResponseDTO> firstPage = foodWebClient.get()
                .uri(urlForPage.apply(1))
                .retrieve()
                .bodyToMono(FoodApiResponseDTO.class);

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

            reactor.core.publisher.Flux<FoodApiResponseDTO> restFlux = remainingPages
                    .concatMap(page -> foodWebClient.get()
                            .uri(urlForPage.apply(page))
                            .retrieve()
                            .bodyToMono(FoodApiResponseDTO.class)
                            .onErrorResume(err -> {
                                System.out.println("[Food] failed to fetch page " + page + " : " + err.getMessage());
                                return reactor.core.publisher.Mono.empty();
                            })
                    );

            return restFlux.collectList().map(listOfPages -> {
                java.util.List<FoodApiResponseDTO.FoodDTO> merged = new java.util.ArrayList<>();
                if (first.getList() != null) merged.addAll(first.getList());
                for (FoodApiResponseDTO p : listOfPages) {
                    if (p.getList() != null) merged.addAll(p.getList());
                }
                FoodApiResponseDTO out = new FoodApiResponseDTO();
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
    
    
    
    
    // public FoodApiResponseDTO food(FoodApiRequestDTO request) {
    //     String url = openApiFoodUrl +
    //             "?type=json" +
    //             "&key=" + appKey;
    //     if (request.getStdRestCd() != "") {
    //         url = url + "&stdRestCd=" + request.getStdRestCd();
    //     }
    //     System.out.println("[WEB CLIENT] GET URL " + url);
        
    //     return foodWebClient.get()
    //             .uri(url)
    //             .retrieve()
    //             .bodyToMono(FoodApiResponseDTO.class)
    //             .block();
    // }
}
