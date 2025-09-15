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
public class RouteRequestDTO {
    private String startX;
    private String startY;
    private String endX;
    private String endY;
}
