package com.mini.mini_2.openapi.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openapi.domain.dto.FacilityApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.FacilityApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.FoodApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiResponseDTO;
import com.mini.mini_2.openapi.service.FacilityApiService;
import com.mini.mini_2.openapi.service.FoodApiService;
import com.mini.mini_2.openapi.service.RestAreaLocationApiService;

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
    
    @Autowired
    private RestAreaLocationApiService restAreaLocationApiService;
    
    @Autowired
    private FacilityApiService facilityApiService;
    
    @GetMapping("location")
    public ResponseEntity<RestAreaLocationApiResponseDTO> restarea(@ModelAttribute RestAreaLocationApiRequestDTO request) {
        RestAreaLocationApiResponseDTO response = restAreaLocationApiService.location(request);
        
        System.out.println("[OPENAPI LOCATION] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("food")
    public ResponseEntity<FoodApiResponseDTO> food(@ModelAttribute FoodApiRequestDTO request) {
        
        FoodApiResponseDTO response = foodApiService.food(request);
        
        System.out.println("[OPENAPI FOOD] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("facility")
    public ResponseEntity<FacilityApiResponseDTO> facility(@ModelAttribute FacilityApiRequestDTO request) {

        FacilityApiResponseDTO response = facilityApiService.facility(request);

        System.out.println("[OPENAPI FACILITY] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
