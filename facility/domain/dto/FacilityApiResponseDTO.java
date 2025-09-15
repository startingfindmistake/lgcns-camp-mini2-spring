package com.mini.mini_2.openapi.facility.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacilityApiResponseDTO {
    
    @JsonProperty("list")
    private List<FoodDTO> list;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("count")
    private String count;
    @Data
    public static class FoodDTO {
        @JsonProperty("stdRestCd")
        private String stdRestCd;
        @JsonProperty("foodNm")
        private String foodNm;
        @JsonProperty("foodCost")
        private String foodCost;
        @JsonProperty("etc")
        private String etc;
        @JsonProperty("recommendyn")
        private String recommendyn;
        @JsonProperty("seasonMenu")
        private String seasonMenu;
        @JsonProperty("bestfoodyn")
        private String bestfoodyn;
        @JsonProperty("premiumyn")
        private String premiumyn;
        @JsonProperty("restCd")
        private String restCd;
        @JsonProperty("foodMaterial")
        private String foodMaterial;
        @JsonProperty("stdRestNm")
        private String stdRestNm;
        @JsonProperty("svarAddr")
        private String svarAddr;
        @JsonProperty("routeCd")
        private String routeCd;
        @JsonProperty("routeNm")
        private String routeNm;

    }
}
