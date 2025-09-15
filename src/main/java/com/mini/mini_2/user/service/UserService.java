package com.mini.mini_2.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.user.domain.dto.UserRequestDTO;
import com.mini.mini_2.user.domain.dto.UserResponseDTO;
import com.mini.mini_2.user.domain.entity.UserEntity;
import com.mini.mini_2.user.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserResponseDTO signup(UserRequestDTO request) {
        System.out.println("[UserService] sign up");
        
        UserEntity entity = userRepository.save(request.toEntity());
        return UserResponseDTO.fromEntity(entity);
    }
    
    public UserResponseDTO signin(UserRequestDTO request) {
        System.out.println("[UserService] sign in");
        
        UserEntity entity = userRepository.findByUserEmailAndPassword(request.getUserEmail(), request.getPassword());
        UserResponseDTO response = UserResponseDTO.fromEntity(entity);
        
        return response;
    }
    
    public UserResponseDTO updatePassword(UserRequestDTO request) {
        System.out.println("[UserService] change password");
        
        UserEntity entity = userRepository.findByUserEmail(request.getUserEmail());

        entity.setUserNickname(request.getUserNickname());
        entity.setPassword(request.getPassword());

        return UserResponseDTO.fromEntity(userRepository.save(entity));
    }
    
    public Integer delete(UserRequestDTO request) {
        System.out.println("[UserService] delete user");
        
        UserEntity entity = userRepository.findByUserEmail(request.getUserEmail());
        
        if (entity == null) return 0;
        
        userRepository.deleteById(entity.getUserId());
        
        return 1;
        
    }
}
