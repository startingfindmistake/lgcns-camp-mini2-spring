package com.mini.mini_2.review.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.review.service.ReviewService;

@RestController
@RequestMapping("/api/v1/mini/review")
public class ReviewCtrl {
    
    @Autowired
    private ReviewService reviewService;
    
    
    
}
