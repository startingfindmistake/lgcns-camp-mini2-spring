package com.mini.mini_2.food.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mini.mini_2.food.domain.entity.FoodEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodResponseDTO {
    
    private Integer foodId;
    private Integer restAreaId;
    private String  foodName;
    private double  price;
    @JsonProperty("signature")
    private boolean isSignature;

    public static FoodResponseDTO fromEntity(FoodEntity entity){
        return FoodResponseDTO.builder()
                    .foodId(entity.getFoodId())
                    .restAreaId(entity.getRestArea() != null ? entity.getRestArea().getRestAreaId() : null)
                    .foodName(entity.getFoodName())
                    .price(entity.getPrice())
                    .isSignature(entity.isSignature())
                    .build();
    }

}
