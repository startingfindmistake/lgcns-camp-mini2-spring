package com.mini.mini_2.favorite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.favorite.domain.dto.FavoriteRequestDTO;
import com.mini.mini_2.favorite.domain.dto.FavoriteResponseDTO;
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.favorite.repository.FavoriteRepository;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public FavoriteResponseDTO post(FavoriteRequestDTO request) {
        System.out.println("[FavoriteService] post");

        FavoriteEntity entity = favoriteRepository.save(request.toEntity());
        return FavoriteResponseDTO.fromEntity(entity);
    }
    
    public void delete(Integer favoriteId) {
        System.out.println("[FavoriteService] delete");
        
        favoriteRepository.deleteById(favoriteId);

    }
}
