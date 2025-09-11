package com.mini.mini_2.favorite.domain.dto;

import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;

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
public class FavoriteResponseDTO {
    private Integer favorite_id;
    
    public static FavoriteResponseDTO fromEntity(FavoriteEntity entity) {
        return FavoriteResponseDTO.builder()
                                  .favorite_id(entity.getFavorite_id())
                                  .build();
    }
}
