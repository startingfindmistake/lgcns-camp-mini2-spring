package com.mini.mini_2.tmapapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoutePoiRequestDTO {
    private String startX;
    private String startY;
    private String endX;
    private String endY;
    private String userX;
    private String userY;
    private String radius;
    private String searchType;
    private String searchKeyword;
    private String lineString;
}