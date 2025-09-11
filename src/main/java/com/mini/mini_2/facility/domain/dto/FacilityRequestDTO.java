package com.mini.mini_2.facility.domain.dto;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;
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

// 어느 휴게소의 시설
public class FacilityRequestDTO {

    private Integer restAreaId;
    private String  name;
    private String  description;
    
    public FacilityEntity toEntity(RestAreaEntity restArea) {
        return FacilityEntity.builder()
                .restArea(restArea)
                .name(this.name)
                .description(this.description)
                .build();
    }
    
}
