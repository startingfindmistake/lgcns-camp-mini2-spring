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
    
    private Integer restAreaId; 
    private String  name; 
    private String  comment; 
    private String  direction ;

    // [편의점, 화장실, ....]
    // private List<String> facilityTypes ; 
    
   
    public static RestAreaResponseDTO fromEntity(RestAreaEntity rest) { 
        return RestAreaResponseDTO.builder()
                .restAreaId(rest.getRestAreaId())
                .name(rest.getName())
                .comment(rest.getComment())
                .direction(rest.getDirection())
                .build() ; 
    }
}


