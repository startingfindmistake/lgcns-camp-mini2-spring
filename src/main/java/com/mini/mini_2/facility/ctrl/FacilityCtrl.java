package com.mini.mini_2.facility.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.domain.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.service.FacilityService;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/mini/facility")
@Tag(name = "Facility API", description = "휴게소 시설 API")

public class FacilityCtrl {

    @Autowired
    private FacilityService facilityService;
    

    @ApiResponses(
        {
            @ApiResponse(responseCode = "204",
                         description = "Post Facility Success"),
            @ApiResponse(responseCode = "404",
                         description = "Post Facility Failed")
        }
    )

    @PostMapping("post")
    public ResponseEntity<Void> post(@RequestBody FacilityRequestDTO request) {
        System.out.println("[FacilityCtrl] post ");
        FacilityResponseDTO response = facilityService.post(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }
    
}
