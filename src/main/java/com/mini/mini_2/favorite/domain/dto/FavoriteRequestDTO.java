package com.mini.mini_2.favorite.domain.dto;

import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.user.domain.entity.UserEntity;

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
public class FavoriteRequestDTO {
    private Integer userId;
    private Integer restAreaId;
    
    public FavoriteEntity toEntity(UserEntity userEntity, RestAreaEntity restAreaEntity) {
        return FavoriteEntity.builder()
                             .user(userEntity)
                             .restArea(restAreaEntity)
                             .build();
    }
}
