package com.mini.mini_2.food.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mini.mini_2.food.domain.entity.FoodEntity;
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
public class FoodRequestDTO {
    
    private Integer restAreaId;  
    private String  foodName;     
    private double  price;
    private boolean isSignature;
    private String description;

    public FoodEntity toEntity(RestAreaEntity restArea) {
        return FoodEntity.builder()
                .restArea(restArea) 
                .foodName(this.foodName)
                .price(this.price)
                .isSignature(this.isSignature)
                .description(this.description)
                .build();
    }
   
}
