package com.mini.mini_2.openapi.facility.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openapi.domain.dto.foodApiRequest;
import com.mini.mini_2.openapi.domain.dto.foodApiResponseDTO;
import com.mini.mini_2.openapi.service.FoodApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v2/mini/openapi")
public class FacilityApiCtrl {
    
    @Autowired
    private FoodApiService foodApiService;
    
    @GetMapping("facility")
    public ResponseEntity<foodApiResponseDTO> facility(@ModelAttribute foodApiRequest request) {
        
        foodApiResponseDTO response = foodApiService.food(request);
        
        System.out.println("[OPENAPI FOOD] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
