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
public class ReviewRequestDTO {
    private int review_id;
    private int rating;
    private String comment;
    
    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                         .review_id(this.review_id)
                         .rating(this.rating)
                         .comment(this.comment)
                         .build();
    }
}
