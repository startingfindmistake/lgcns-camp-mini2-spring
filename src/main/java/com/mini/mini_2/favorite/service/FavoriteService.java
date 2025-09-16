package com.mini.mini_2.favorite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.favorite.domain.dto.FavoriteRequestDTO;
import com.mini.mini_2.favorite.domain.dto.FavoriteResponseDTO;
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.favorite.repository.FavoriteRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.user.domain.entity.UserEntity;
import com.mini.mini_2.user.repository.UserRepository;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private RestAreaRepository restAreaRepository;
    
    public FavoriteResponseDTO create(FavoriteRequestDTO request) {
        System.out.println("[FavoriteService] post");
        
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        Optional<RestAreaEntity> restAreaEntity = restAreaRepository.findById(request.getRestAreaId());
        

        FavoriteEntity entity = favoriteRepository.save(request.toEntity(userEntity.get(), restAreaEntity.get()));
        return FavoriteResponseDTO.fromEntity(entity);
    }
    
    public void delete(Integer favoriteId) {
        System.out.println("[FavoriteService] delete");
        
        favoriteRepository.deleteById(favoriteId);
        
    }
    
    public List<FavoriteResponseDTO> findAllById(Integer userId) {
        System.out.println("[FavoriteService] find favorites");

        List<FavoriteEntity> entities = favoriteRepository.findAllByUser_UserId(userId);
        
        return entities.stream()
                       .map(entity -> FavoriteResponseDTO.fromEntity(entity))
                       .toList();
        
    }
}
