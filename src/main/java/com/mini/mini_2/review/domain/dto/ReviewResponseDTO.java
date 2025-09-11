package com.mini.mini_2.review.domain.dto;

import com.mini.mini_2.review.domain.entity.ReviewEntity;

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
public class ReviewResponseDTO {
    private Integer reviewId;

    private Integer userId;
    private Integer restAreaId;

    private String rating;
    private String comment;
    
    public static ReviewResponseDTO fromEntity(ReviewEntity entity) {
        return ReviewResponseDTO.builder()
                                .reviewId(entity.getReviewId())
                                .userId(entity.getUser().getUserId())
                                .restAreaId(entity.getRestArea().getRestAreaId())
                                .rating(entity.getRating())
                                .comment(entity.getComment())
                                .build();
    }
}
