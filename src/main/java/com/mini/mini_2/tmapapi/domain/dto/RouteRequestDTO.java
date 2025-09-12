package com.mini.mini_2.tmapapi.domain.dto;

import java.util.List;
import java.util.Properties;

import org.mariadb.jdbc.type.Geometry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder.Default;

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
