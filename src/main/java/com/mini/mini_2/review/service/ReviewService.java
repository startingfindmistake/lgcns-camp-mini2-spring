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
    
    // 생성
    public ReviewResponseDTO post(ReviewRequestDTO request) {
        System.out.println("[ReviewService] post");
        
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        Optional<RestAreaEntity> restAreaEntity = restAreaRepository.findById(request.getRestAreaId());
        

        ReviewEntity entity = reviewRepository.save(request.toEntity(userEntity.get(), restAreaEntity.get()));
        return ReviewResponseDTO.fromEntity(entity);
    }

    // 정렬(sort): 최신순(기본)/ 평점순(선택) 
    public List<ReviewResponseDTO> findByRestAreaId(Integer restAreaId, String sort) {
        System.out.println("[ReviewService] findByRestAreaId ");
        List<ReviewEntity> responses =
                ("ratingDesc".equalsIgnoreCase(sort))
                        ? reviewRepository.findByRestArea_RestAreaIdOrderByRatingDesc(restAreaId)     
                        : reviewRepository.findByRestArea_RestAreaIdOrderByCreatedAtDesc(restAreaId);
        return responses.stream()
                        .map(ReviewResponseDTO::fromEntity)
                        .toList();
    }
    
    // user별 작성한 리뷰 조회
    public List<ReviewResponseDTO> findByUserId(Integer userId) {

        List<ReviewEntity> responses = reviewRepository.findByUser_userId(userId);

        return responses.stream()
                .map(entity -> ReviewResponseDTO.fromEntity(entity))
                .toList();
    }

    // 수정
    public ReviewResponseDTO update(Integer reviewId, ReviewRequestDTO request) {
        System.out.println("[ReviewService] update ");

        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다. ID = " + reviewId));
        
        reviewEntity.setRating(request.getRating());
        reviewEntity.setComment(request.getComment());

        return ReviewResponseDTO.fromEntity(reviewRepository.save(reviewEntity)) ;

    }
   
    // 삭제
    public Void delete(Integer reviewId) {
        
        reviewRepository.deleteById(reviewId);
        
        return null;
    }
}
