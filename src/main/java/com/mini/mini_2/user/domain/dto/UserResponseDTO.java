package com.mini.mini_2.user.domain.dto;

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
public class UserResponseDTO {
    private Integer userId;
    private String password;
    private String username;
    
    public static UserResponseDTO fromEntity(UserEntity entity) {
        return UserResponseDTO.builder()
                              .userId(entity.getUserId())
                              .password(entity.getPassword())
                              .username(entity.getUsername())
                              .build();
    }
}
