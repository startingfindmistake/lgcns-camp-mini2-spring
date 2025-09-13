package com.mini.mini_2.openapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestAreaLocationApiResponseDTO {
    @JsonProperty("list")
    private List<RestAreaLocationDTO> list;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("count")
    private String count;
    
    @Data
    public static class RestAreaLocationDTO{
        @JsonProperty("unitName")
        private String unitName;
        @JsonProperty("unitCode")
        private String unitCode;
        @JsonProperty("routeName")
        private String routeName;
        @JsonProperty("routeNo")
        private String routeNo;
        @JsonProperty("xValue")
        private String xValue;
        @JsonProperty("yValue")
        private String yValue;
        @JsonProperty("stdRestCd")
        private String stdRestCd;
        @JsonProperty("serviceAreaCode")
        private String serviceAreaCode;
    }
}