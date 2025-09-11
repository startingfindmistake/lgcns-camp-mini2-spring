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
    private String password;
    private String userEmail;
    private String userNickname;
    
    public UserEntity toEntity() {
        return UserEntity.builder()
                         .password(this.password)
                         .userEmail(this.userEmail)
                         .userNickname(this.userNickname)
                         .build();
    }
}
