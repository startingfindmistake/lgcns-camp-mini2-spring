package com.mini.mini_2.openapi.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openapi.domain.dto.FoodApiRequest;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO;
import com.mini.mini_2.openapi.service.FoodApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("api/v2/mini/openapi")
public class OpenApiCtrl {
    
    @Autowired
    private FoodApiService foodApiService;
    
    @GetMapping("food")
    public ResponseEntity<FoodApiResponseDTO> food(@ModelAttribute FoodApiRequest request) {
        
        FoodApiResponseDTO response = foodApiService.food(request);
        
        System.out.println("[OPENAPI FOOD] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("facility")
    public ResponseEntity<FoodApiResponseDTO> facility(@ModelAttribute FoodApiRequest request) {

        FoodApiResponseDTO response = foodApiService.food(request);

        System.out.println("[OPENAPI FOOD] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
