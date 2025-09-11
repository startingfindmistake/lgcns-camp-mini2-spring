package com.mini.mini_2.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.review.domain.dto.ReviewRequestDTO;
import com.mini.mini_2.review.domain.dto.ReviewResponseDTO;
import com.mini.mini_2.review.domain.entity.ReviewEntity;
import com.mini.mini_2.review.repository.ReviewRepository;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public ReviewResponseDTO post(ReviewRequestDTO request) {
        System.out.println("[ReviewService] post");

        ReviewEntity entity = reviewRepository.save(request.toEntity());
        return ReviewResponseDTO.fromEntity(entity);
    }
}
