package com.mini.mini_2.review.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.review.domain.dto.ReviewRequestDTO;
import com.mini.mini_2.review.domain.dto.ReviewResponseDTO;
import com.mini.mini_2.review.domain.entity.ReviewEntity;
import com.mini.mini_2.review.repository.ReviewRepository;
import com.mini.mini_2.user.domain.entity.UserEntity;
import com.mini.mini_2.user.repository.UserRepository;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private RestAreaRepository restAreaRepository;
    
    public ReviewResponseDTO post(ReviewRequestDTO request) {
        System.out.println("[ReviewService] post");
        
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        Optional<RestAreaEntity> restAreaEntity = restAreaRepository.findById(request.getRestAreaId());
        


        ReviewEntity entity = reviewRepository.save(request.toEntity(userEntity.get(), restAreaEntity.get()));
        return ReviewResponseDTO.fromEntity(entity);
    }
    
    public List<ReviewResponseDTO> findByRestAreaId(Integer restAreaId) {
        
        List<ReviewEntity> responses = reviewRepository.findByRestArea_RestAreaId(restAreaId);
        
        return responses.stream()
                        .map(entity -> ReviewResponseDTO.fromEntity(entity))
                        .toList();
    }
    
    public List<ReviewResponseDTO> findByUserId(Integer userId) {

        List<ReviewEntity> responses = reviewRepository.findByUser_userId(userId);

        return responses.stream()
                .map(entity -> ReviewResponseDTO.fromEntity(entity))
                .toList();
    }
    
    public Void delete(Integer reviewId) {
        
        reviewRepository.deleteById(reviewId);
        
        return null;
    }
}
