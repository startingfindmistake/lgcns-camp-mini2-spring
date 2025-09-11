package com.mini.mini_2.facility.domain.dto;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;

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
public class FacilityResponseDTO {
    private Integer  facilityId;
    private Integer  restAreaId;
    private String   name;
    private String   description;
    
    public static FacilityResponseDTO fromEntity(FacilityEntity entity) {
        return FacilityResponseDTO.builder()
                                  .facilityId(entity.getFacilityId())
                                  .restAreaId(entity.getRestArea().getRestAreaId())
                                  .name(entity.getName())
                                  .description(entity.getDescription())
                                  .build();
    }
    
}
