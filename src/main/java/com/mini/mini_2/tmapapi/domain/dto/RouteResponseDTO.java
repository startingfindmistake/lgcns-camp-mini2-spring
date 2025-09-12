package com.mini.mini_2.tmapapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RouteResponseDTO {
    @JsonProperty("usedFavoriteRouteVertices")
    private String usedFavoriteRouteVertices;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("features")
    private List<FeatureDTO> features;

    @Data
    public static class FeatureDTO {
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("geometry")
        private GeometryDTO geometry;

        @JsonProperty("properties")
        private PropertiesDTO properties;
    }

    @Data
    public static class GeometryDTO {
        @JsonProperty("type")
        private String type;

        @JsonProperty("coordinates")
        private List<Object> coordinates;

        @JsonProperty("traffic")
        private List<List<Integer>> traffic;
    }

    @Data
    public static class PropertiesDTO {
        @JsonProperty("index")
        private Integer index;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("totalDistance")
        private Integer totalDistance;

        @JsonProperty("totalTime")
        private Integer totalTime;

        @JsonProperty("totalFare")
        private Integer totalFare;

        @JsonProperty("taxiFare")
        private Integer taxiFare;

        @JsonProperty("pointIndex")
        private Integer pointIndex;

        @JsonProperty("nextRoadName")
        private String nextRoadName;

        @JsonProperty("turnType")
        private Integer turnType;

        @JsonProperty("pointType")
        private String pointType;

        @JsonProperty("lineIndex")
        private Integer lineIndex;

        @JsonProperty("distance")
        private Integer distance;

        @JsonProperty("time")
        private Integer time;

        @JsonProperty("roadType")
        private Integer roadType;

        @JsonProperty("facilityType")
        private Integer facilityType;
    }
}