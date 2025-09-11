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
public class UserRequestDTO {
    private Integer user_id;
    private String password;
    private String username;
    
    public UserEntity toEntity() {
        return UserEntity.builder()
                         .user_id(this.user_id)
                         .password(this.password)
                         .username(this.username)
                         .build();
    }
}
