package com.mini.mini_2.openapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacilityApiResponseDTO {
    
    @JsonProperty("list")
    private List<FacilityDTO> list;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("count")
    private String count;
    
    @Data
    public static class FacilityDTO {
        @JsonProperty("stdRestCd")
        private String stdRestCd;
        @JsonProperty("stime")
        private String stime;
        @JsonProperty("routeCd")
        private String routeCd;
        @JsonProperty("svarAddr")
        private String svarAddr;
        @JsonProperty("routeNm")
        private String routeNm;
        @JsonProperty("stdRestNm")
        private String stdRestNm;
        @JsonProperty("etime")
        private String etime;
        @JsonProperty("redId")
        private String redId;
        @JsonProperty("redDtime")
        private String redDtime;
        @JsonProperty("lsttmAltrDttm")
        private String lsttmAltrDttm;
        @JsonProperty("psCode")
        private String psCode;
        @JsonProperty("psName")
        private String psName;
        @JsonProperty("psDesc")
        private String psDesc;
    }
}