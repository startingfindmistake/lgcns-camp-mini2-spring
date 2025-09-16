package com.mini.mini_2.tmapapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoiResponseDTO {
    @JsonProperty("searchPoiInfo")
    private SearchPoiInfo searchPoiInfo;
    
    @Data
    public static class SearchPoiInfo {
        @JsonProperty("totalCount")
        private String totalCount;

        @JsonProperty("count")
        private String count;

        @JsonProperty("pois")
        private Pois pois;
    }
    
    @Data
    public static class Pois {
        @JsonProperty("poi")
        private List<Poi> poi;
    }

    @Data
    public static class Poi {
        @JsonProperty("name")
        private String name;
        @JsonProperty("noorLat")
        private String noorLat;
        @JsonProperty("noorLon")
        private String noorLon;
    }

}
