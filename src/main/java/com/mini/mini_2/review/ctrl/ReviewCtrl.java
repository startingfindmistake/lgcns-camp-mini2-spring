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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



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
    @PostMapping("post")
    public ResponseEntity post(@RequestBody ReviewRequestDTO request) {
        System.out.println("[ReviewCtrl] post : " + request);
        ReviewResponseDTO response = reviewService.post(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }
    
    @GetMapping("getReviewByRestArea/{rest_area_id}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewByRestArea(@PathVariable("rest_area_id") int rest_area_id) {
        System.out.println("[ReviewCtrl] getReviewByRestArea : id -> " + rest_area_id);
        
        // TODO: after initialize rest_area table.
        
        
        return null;
    }
    
    
}
