package com.mini.mini_2.rest_area.domain.dto;


import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

import jakarta.persistence.Column;
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
    
    private String name;
    private String direction;
    private String code;
    private String tel ;
    private String address ;
    private String routeName ;
    
    
    
    
    
    public RestAreaEntity toEntity() {
        return RestAreaEntity.builder()
                             .name(this.name)
                             .direction(this.direction)
                             .code(this.code)
                             .tel(this.tel)
                             .address(this.address)
                             .routeName(this.routeName)
                             .build();
    }
}

