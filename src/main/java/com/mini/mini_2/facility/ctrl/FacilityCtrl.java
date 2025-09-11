package com.mini.mini_2.facility.ctrl;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


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

    @PostMapping("create")
    public ResponseEntity<Void> create(@RequestBody FacilityRequestDTO request) {
        System.out.println("[FacilityCtrl] create ");
        FacilityResponseDTO response = facilityService.post(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }
    
    @GetMapping("facilities/{rest_area_id}")
    public ResponseEntity<List<FacilityResponseDTO>> facilities(@PathVariable("rest_area_id") Integer rest_area_id) {
        System.out.println("[FacilityCtrl] find all ");
        
        List<FacilityResponseDTO> responses = facilityService.find(rest_area_id);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    
}
