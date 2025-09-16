package com.mini.mini_2.all;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.review.domain.dto.ReviewRequestDTO;
import com.mini.mini_2.review.domain.dto.ReviewResponseDTO;
import com.mini.mini_2.review.domain.entity.ReviewEntity;
import com.mini.mini_2.review.repository.ReviewRepository;
import com.mini.mini_2.review.service.ReviewService;
import com.mini.mini_2.user.domain.entity.UserEntity;
import com.mini.mini_2.user.repository.UserRepository;


@SpringBootTest
@Transactional
@Rollback
public class ReviewTest {

    @Autowired 
    private ReviewService reviewService;
    @Autowired 
    private ReviewRepository reviewRepository;
    @Autowired 
    private RestAreaRepository restAreaRepository;

    // 실제 더미 유저 생성
    @Autowired 
    private UserRepository userRepository;

    @Test
    public void test() {

        UserEntity user = UserEntity.builder()
                .userEmail("hn7" + System.currentTimeMillis() + "@naver.com")
                .password("12345!")
                .userNickname("hn7")
                .build();

        user = userRepository.save(user);
        Integer userId = user.getUserId(); 


        // 1) 휴게소 생성
        RestAreaEntity rest1 = RestAreaEntity.builder()
                .name("횡성휴게소")
                .direction("상행")
                .code("re011")
                .tel("033-555-7777")
                .address("강원도 횡성군")
                .routeName("영동고속도로")
                .build();
        rest1 = restAreaRepository.save(rest1);

        // 2) 리뷰 작성
        ReviewRequestDTO request1 = ReviewRequestDTO.builder()
                .userId(userId)
                .restAreaId(rest1.getRestAreaId())
                .rating("4.0")
                .comment("한우버거 맛있음")
                .build();
        ReviewResponseDTO created = reviewService.create(request1);
        assertNotNull(created.getReviewId());

        // 3) 사용자별 조회
        List<ReviewResponseDTO> byUser = reviewService.findByUserId(userId);
        assertTrue(byUser.stream().anyMatch(r -> r.getReviewId().equals(created.getReviewId())));

        // 4) 휴게소별 최신순
        List<ReviewResponseDTO> latest = reviewService.findByRestAreaId(rest1.getRestAreaId(), "latest");
        assertFalse(latest.isEmpty());

        // 5) 휴게소별 평점순
        List<ReviewResponseDTO> ratingSorted = reviewService.findByRestAreaId(rest1.getRestAreaId(), "ratingDesc");
        assertFalse(ratingSorted.isEmpty());

        // 6) 수정
        ReviewRequestDTO request = ReviewRequestDTO.builder()
                .rating("5.0")
                .comment("한우버거 & 라면 최고")
                .build();

        ReviewResponseDTO updated = reviewService.update(created.getReviewId(), request);
        assertEquals("5.0", updated.getRating());
        assertTrue(updated.getComment().contains("최고"));        

        // 7) 삭제
        reviewService.delete(created.getReviewId());

        Optional<ReviewEntity> afterDelete = reviewRepository.findById(created.getReviewId());
        assertTrue(afterDelete.isEmpty());
    }
    
}
