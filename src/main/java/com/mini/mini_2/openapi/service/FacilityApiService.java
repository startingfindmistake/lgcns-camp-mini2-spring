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
        return facilityReactive(request).block();
    }

    public reactor.core.publisher.Mono<FacilityApiResponseDTO> facilityReactive(FacilityApiRequestDTO request) {
        final int pageSize = 99; 

        java.util.function.IntFunction<String> urlForPage = (pageNo) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(openApiFacilityUrl)
              .append("?type=json")
              .append("&key=").append(appKey)
              .append("&numOfRows=").append(pageSize)
              .append("&pageNo=").append(pageNo);
            if (request.getStdRestCd() != null && !request.getStdRestCd().isBlank()) {
                sb.append("&stdRestCd=").append(request.getStdRestCd());
            }
            return sb.toString();
        };

        reactor.core.publisher.Mono<FacilityApiResponseDTO> firstPage = facilityWebClient.get()
                .uri(urlForPage.apply(1))
                .retrieve()
                .bodyToMono(FacilityApiResponseDTO.class);

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

            reactor.core.publisher.Flux<FacilityApiResponseDTO> restFlux = remainingPages
                    .concatMap(page -> facilityWebClient.get()
                            .uri(urlForPage.apply(page))
                            .retrieve()
                            .bodyToMono(FacilityApiResponseDTO.class)
                            .onErrorResume(err -> {
                                System.out.println("[Facility] failed to fetch page " + page + " : " + err.getMessage());
                                return reactor.core.publisher.Mono.empty();
                            })
                    );

            return restFlux.collectList().map(listOfPages -> {
                java.util.List<FacilityApiResponseDTO.FacilityDTO> merged = new java.util.ArrayList<>();
                if (first.getList() != null) merged.addAll(first.getList());
                for (FacilityApiResponseDTO p : listOfPages) {
                    if (p.getList() != null) merged.addAll(p.getList());
                }
                FacilityApiResponseDTO out = new FacilityApiResponseDTO();
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
    
    
    // public FacilityApiResponseDTO facility(FacilityApiRequestDTO request) {
    //     String url = openApiFacilityUrl +
    //             "?type=json" +
    //             "&key=" + appKey;
    //     if (request.getStdRestCd() != "") {
    //         url = url + "&stdRestCd=" + request.getStdRestCd();
    //     }
    //     System.out.println("[WEB CLIENT] GET URL " + url);
        
    //     return facilityWebClient.get()
    //             .uri(url)
    //             .retrieve()
    //             .bodyToMono(FacilityApiResponseDTO.class)
    //             .block();
    // }
}
