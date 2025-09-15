package com.mini.mini_2.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mini.mini_2.review.domain.entity.ReviewEntity;



@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    
    // 기본 조회
    List<ReviewEntity> findByRestArea_RestAreaId(Integer restAreaId);
    List<ReviewEntity> findByUser_userId(Integer userId);

    // 최신순(정렬)
    List<ReviewEntity> findByRestArea_RestAreaIdOrderByCreatedAtDesc(Integer restAreaId);
    // 평점순(정렬)
    List<ReviewEntity> findByRestArea_RestAreaIdOrderByRatingDesc(Integer restAreaId);
}
