package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiResponseDTO;

@Service
public class RestAreaInfoApiService {
    private final WebClient restAreaInfoWebClient;
    private final String openApiRestAreaInfoUrl;
    private final String appKey;

    public RestAreaInfoApiService(WebClient.Builder builder,
            @Value("${OPENAPI_KEY}") String appKey,
            @Value("${OPENAPI_INFO_URL}") String openApiRestAreaUrl) {        
        this.appKey = appKey;
        this.openApiRestAreaInfoUrl = openApiRestAreaUrl;
        this.restAreaInfoWebClient = builder
                .build();
    }
    
    
    public RestAreaInfoApiResponseDTO info(RestAreaInfoApiRequestDTO request) {
        return infoReactive(request).block();
    }

    public reactor.core.publisher.Mono<RestAreaInfoApiResponseDTO> infoReactive(RestAreaInfoApiRequestDTO request) {
        final int pageSize = 99; 

        java.util.function.IntFunction<String> urlForPage = (pageNo) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(openApiRestAreaInfoUrl)
              .append("?type=json")
              .append("&key=").append(appKey)
              .append("&svarGsstClssCd=0")
              .append("&numOfRows=").append(pageSize)
              .append("&pageNo=").append(pageNo);
            if (request.getSvarCd() != null && !request.getSvarCd().isBlank()) {
                sb.append("&svarCd=").append(request.getSvarCd());
            }
            return sb.toString();
        };

        reactor.core.publisher.Mono<RestAreaInfoApiResponseDTO> firstPage = restAreaInfoWebClient.get()
                .uri(urlForPage.apply(1))
                .retrieve()
                .bodyToMono(RestAreaInfoApiResponseDTO.class);

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

            reactor.core.publisher.Flux<RestAreaInfoApiResponseDTO> restFlux = remainingPages
                    .concatMap(page -> restAreaInfoWebClient.get()
                            .uri(urlForPage.apply(page))
                            .retrieve()
                            .bodyToMono(RestAreaInfoApiResponseDTO.class)
                            .onErrorResume(err -> {
                                System.out.println("[RestArea] failed to fetch page " + page + " : " + err.getMessage());
                                return reactor.core.publisher.Mono.empty();
                            })
                    );

            return restFlux.collectList().map(listOfPages -> {
                java.util.List<RestAreaInfoApiResponseDTO.RestAreaInfoDTO> merged = new java.util.ArrayList<>();
                if (first.getList() != null) merged.addAll(first.getList());
                for (RestAreaInfoApiResponseDTO p : listOfPages) {
                    if (p.getList() != null) merged.addAll(p.getList());
                }
                RestAreaInfoApiResponseDTO out = new RestAreaInfoApiResponseDTO();
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
}
