package com.mini.mini_2.review.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }
    
    @GetMapping("reviewsByRestAreaId/{restAreaId}")
    public ResponseEntity<List<ReviewResponseDTO>> reviewsByRestAreaId(@PathVariable("restAreaId") Integer restAreaId) {
        System.out.println("[ReviewCtrl] reviewsByRestAreaId : id -> " + restAreaId);
        
        List<ReviewResponseDTO> responses = reviewService.findByRestAreaId(restAreaId);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    
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
    
    @DeleteMapping("delete/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable("reviewId") Integer reviewId) {
        System.out.println("[ReviewCtrl] reviewsByUserId : id -> " + reviewId);
        
        reviewService.delete(reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    
    
}
