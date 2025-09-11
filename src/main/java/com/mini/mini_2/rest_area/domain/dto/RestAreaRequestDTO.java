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
public class RestAreaRequestDTO {
    
    private Integer restAreaId;
    private String name;
    private String comment;
    private String direction;
    
    public RestAreaEntity toEntity() {
        return RestAreaEntity.builder()
                             .restAreaId(this.restAreaId)
                             .name(this.name)
                             .comment(this.comment)
                             .direction(this.direction)
                             .build();
    }
}

