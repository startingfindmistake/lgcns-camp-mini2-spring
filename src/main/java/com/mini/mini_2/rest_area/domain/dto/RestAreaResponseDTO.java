package com.mini.mini_2.rest_area.domain.dto;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

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
public class RestAreaResponseDTO {
    
    private Integer restAreaId ;
    private String  name; 
    private String  comment; 
    private String  direction ;
    
   
    public static RestAreaResponseDTO fromEntity(RestAreaEntity entity) { 
        return RestAreaResponseDTO.builder()
                .restAreaId(entity.getRest_area_id())
                .name(entity.getName())
                .comment(entity.getComment())
                .direction(entity.getDirection())
                .build() ; 
    }
}
