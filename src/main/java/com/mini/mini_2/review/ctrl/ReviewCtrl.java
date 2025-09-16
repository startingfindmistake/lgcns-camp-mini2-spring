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
@Tag(name = "Review API", description = "리뷰 API")
public class ReviewCtrl {
    
    @Autowired
    private ReviewService reviewService;
    
    @Operation(
        summary = "리뷰 작성",
        description = "리뷰를 작성해주세요."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Post Review Success"),
            @ApiResponse(responseCode = "404",
                         description = "Post Review Failed")
        }
    )
    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody ReviewRequestDTO request) {
        System.out.println("[ReviewCtrl] create : " + request);
        ReviewResponseDTO response = reviewService.create(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }

    @Operation(
        summary = "휴게소 ID 기반 리뷰 목록 조회",
        description = "휴게소 ID와 정렬방법(최신순 or 평점순)을 입력해주세요."
    )
    
    @GetMapping("lists/{restAreaId}")
    public ResponseEntity<List<ReviewResponseDTO>> findByRestAreaId(
            @PathVariable("restAreaId") Integer restAreaId,
            @RequestParam(name= "sort", defaultValue = "최신순") String sort) {
        System.out.println("[ReviewCtrl] findByRestAreaId : " + restAreaId);
        System.out.println("[ReviewCtrl] sort : " + sort);        

        List<ReviewResponseDTO> responses = reviewService.findByRestAreaId(restAreaId, sort);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    
    @Operation(
        summary = "회원 ID 기반 리뷰 목록 조회",
        description = "회원 ID를 입력해주세요."
    )

    @GetMapping("/lists/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> findByUserId(@PathVariable("userId") Integer userId) {
        System.out.println("[ReviewCtrl] reviewsByUserId : " + userId);
        
        List<ReviewResponseDTO> responses = reviewService.findByUserId(userId);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

    @Operation(
        summary = "리뷰 정보 수정",
        description = "리뷰 ID를 입력해주세요."
    )

    @PostMapping("/update/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> update(
            @PathVariable("reviewId") Integer reviewId,
            @RequestBody ReviewRequestDTO request) {

        System.out.println("[ReviewCtrl] update reviewId : "+ reviewId);    
        System.out.println("[ReviewCtrl] update request : "+ request);

        try {
            ReviewResponseDTO response = reviewService.update(reviewId, request) ;
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
        summary = "리뷰 정보 삭제",
        description = "리뷰 ID를 입력해주세요."
    )
    
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable("reviewId") Integer reviewId) {
        System.out.println("[ReviewCtrl] delete : " + reviewId);
        
        reviewService.delete(reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    
    
}
