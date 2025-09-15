package com.mini.mini_2.food.domain.dto;

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
    private String foodName;
    private String price;
    private String isSignature;
    private String description;

    public static FoodResponseDTO fromEntity(FoodEntity entity) {
        return FoodResponseDTO.builder()
                .foodId(entity.getFoodId())
                .restAreaId(entity.getRestArea().getRestAreaId())
                .foodName(entity.getFoodName())
                .price(entity.getPrice())
                .isSignature(entity.getIsSignature())
                .description(entity.getDescription())
                .build();
    }

}
