package com.mini.mini_2.tmapapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class RouteResponseDTO {
    private String usedFavoriteRouteVertices;
    private String type;
    private List<FeatureDTO> features;

    @Data
    public static class FeatureDTO {
        private String type;
        private GeometryDTO geometry;
        private PropertiesDTO properties;
    }

    @Data
    public static class GeometryDTO {
        private String type;
        private List<Object> coordinates;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<List<Integer>> traffic;
    }

    @Data
    public static class PropertiesDTO {
        private Integer index;
        private String name;
        private String description;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer totalDistance;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer totalTime;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer totalFare;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer taxiFare;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer pointIndex;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String nextRoadName;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer turnType;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String pointType;

        // LineString 전용
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer lineIndex;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer distance;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer time;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer roadType;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer facilityType;
    }
}
// @Data
// public class RouteResponseDTO {
//     private String usedFavoriteRouteVertices;
//     private String type;
//     private List<FeatureDTO> features;

//     @Data
//     public static class FeatureDTO {
//         private String type;
//         private GeometryDTO geometry;
//         private PropertiesDTO properties;
//     }

//     @Data
//     public static class GeometryDTO {
//         private String type;
//         private List<Object> coordinates;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private List<List<Integer>> traffic;
//     }

//     @Data
//     public static class PropertiesDTO {
//         private Integer index;
//         private String name;
//         private String description;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer totalDistance;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer totalTime;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer totalFare;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer taxiFare;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer pointIndex;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private String nextRoadName;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer turnType;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private String pointType;

//         // LineString 전용
//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer lineIndex;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer distance;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer time;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer roadType;

//         @JsonInclude(JsonInclude.Include.NON_NULL)
//         private Integer facilityType;
//     }
// }