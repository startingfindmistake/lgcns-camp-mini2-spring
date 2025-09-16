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
    
    private String name;
    private String direction;
    private String code;
    private String tel;
    private String address;
    private String routeName;
    private String xValue;
    private String yValue;

    // [편의점, 화장실, ....]
    // private List<String> facilityTypes ; 
    
   
    public static RestAreaResponseDTO fromEntity(RestAreaEntity entity) { 
        return RestAreaResponseDTO.builder()
                                  .restAreaId(entity.getRestAreaId())
                                  .name(entity.getName())
                                  .direction(entity.getDirection())
                                  .code(entity.getCode())
                                  .tel(entity.getTel())
                                  .address(entity.getAddress())
                                  .routeName(entity.getRouteName())
                                  .xValue(entity.getXValue())
                                  .yValue(entity.getYValue())
                                  .build() ; 
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RestAreaResponseDTO that = (RestAreaResponseDTO) o;
        return restAreaId != null && restAreaId.equals(that.restAreaId);
    }
    
    @Override
    public int hashCode() {
        return restAreaId != null ? restAreaId.hashCode() : 0;
    }
}


