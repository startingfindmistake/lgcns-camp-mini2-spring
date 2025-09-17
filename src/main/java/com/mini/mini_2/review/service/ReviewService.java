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
    
    // 리뷰 작성
    public ReviewResponseDTO create(ReviewRequestDTO request) {
        System.out.println("[ReviewService] create : "+ request);
        
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        Optional<RestAreaEntity> restAreaEntity = restAreaRepository.findById(request.getRestAreaId());
        

        ReviewEntity entity = reviewRepository.save(request.toEntity(userEntity.get(), restAreaEntity.get()));
        return ReviewResponseDTO.fromEntity(entity);
    }

    // ID 기반 리뷰 정렬 조회
    public List<ReviewResponseDTO> findByRestAreaId(Integer restAreaId, String sort) {
        System.out.println("[RestAreaService] findByRestAreaId : "+ restAreaId);
        System.out.println("[RestAreaService] sort : "+ sort);
        List<ReviewEntity> responses =
                ("평점순".equalsIgnoreCase(sort))
                        ? reviewRepository.findByRestArea_RestAreaIdOrderByRatingDesc(restAreaId)     
                        : reviewRepository.findByRestArea_RestAreaIdOrderByCreatedAtDesc(restAreaId);
        return responses.stream()
                        .map(ReviewResponseDTO::fromEntity)
                        .toList();
    }
    
    // ID 기반 휴게소 단건 조회
    public List<ReviewResponseDTO> findByUserId(Integer userId) {

        List<ReviewEntity> responses = reviewRepository.findByUser_UserId(userId);

        return responses.stream()
                .map(entity -> ReviewResponseDTO.fromEntity(entity))
                .toList();
    }

    // 리뷰 수정
    public ReviewResponseDTO update(Integer reviewId, ReviewRequestDTO request) {
        System.out.println("[RestAreaService] update reviewId : "+ reviewId);
        System.out.println("[RestAreaService] update : "+ request);

        ReviewEntity entity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다. ID = " + reviewId));
        
        entity.setRating(request.getRating());
        entity.setComment(request.getComment());

        return ReviewResponseDTO.fromEntity(reviewRepository.save(entity)) ;

    }
   
    // 리뷰 삭제
    public Void delete(Integer reviewId) {
        
        reviewRepository.deleteById(reviewId);
        
        return null;
    }
}
