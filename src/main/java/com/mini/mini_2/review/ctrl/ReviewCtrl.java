package com.mini.mini_2.review.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.review.domain.dto.ReviewRequestDTO;
import com.mini.mini_2.review.domain.dto.ReviewResponseDTO;
import com.mini.mini_2.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/mini/review")
@Tag(name = "Review API", description = "Review API Documentation")
public class ReviewCtrl {
    
    @Autowired
    private ReviewService reviewService;
    
    @Operation(
        summary = "Review Post",
        description = "Review Post"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Post Review Success"),
            @ApiResponse(responseCode = "404",
                         description = "Post Review Failed")
        }
    )
    @PostMapping("create")
    public ResponseEntity create(@RequestBody ReviewRequestDTO request) {
        System.out.println("[ReviewCtrl] post : " + request);
        ReviewResponseDTO response = reviewService.post(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }

    @Operation(
        summary = "휴게소별 리뷰(기본: 최신순/ 선택: 평점순)",
        description = "휴게소별 리뷰(기본: 최신순/ 선택: 평점순)"
    )
    
    @GetMapping("reviewsByRestAreaId/{restAreaId}")
    public ResponseEntity<List<ReviewResponseDTO>> reviewsByRestAreaId(
            @PathVariable("restAreaId") Integer restAreaId,
            @RequestParam(name= "sort", defaultValue = "latest") String sort) {
        System.out.println("[ReviewCtrl] reviewsByRestAreaId : id -> " + restAreaId + ", sort -> " + sort);
        
        List<ReviewResponseDTO> responses = reviewService.findByRestAreaId(restAreaId, sort);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    
    @Operation(
        summary = "사용자별 작성한 리뷰 조회",
        description = "사용자별 작성한 리뷰 조회"
    )

    @GetMapping("reviewsByUserId/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> reviewsByUserId(@PathVariable("userId") Integer userId) {
        System.out.println("[ReviewCtrl] reviewsByUserId : id -> " + userId);
        
        List<ReviewResponseDTO> responses = reviewService.findByUserId(userId);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

    @Operation(
        summary = "리뷰 수정",
        description = "리뷰 수정"
    )

    @PostMapping("update/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> update(
            @PathVariable("reviewId") Integer reviewId,
            @RequestBody ReviewRequestDTO request) {
        System.out.println("[ReviewCtrl] update : id -> " + reviewId);

        try {
            ReviewResponseDTO response = reviewService.update(reviewId, request) ;
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
        summary = "리뷰 삭제",
        description = "리뷰 삭제"
    )
    
    @DeleteMapping("delete/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable("reviewId") Integer reviewId) {
        System.out.println("[ReviewCtrl] reviewsByUserId : id -> " + reviewId);
        
        reviewService.delete(reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    
    
}
