package com.mini.mini_2.facility.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.domain.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.service.FacilityService;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/mini/facility")
@Tag(name = "Facility API", description = "편의시설 API")

public class FacilityCtrl {

    @Autowired
    private FacilityService facilityService;

    @Operation(
        summary = "편의시설 생성",
        description = "편의시설을 생성해주세요."
    )

    @ApiResponses(
        {
            @ApiResponse(responseCode = "201",
                         description = "Post Facility Success"),
            @ApiResponse(responseCode = "500",
                         description = "Post Facility Failed")
        }
    )

    @PostMapping("/create")
    public ResponseEntity<FacilityResponseDTO> create(@RequestBody FacilityRequestDTO request) {
        System.out.println("[FacilityCtrl] create : "+ request);
        FacilityResponseDTO response = facilityService.create(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }

    @Operation(
        summary = "휴게소ID 기반 편의시설 목록 조회",
        description = "휴게소 ID를 입력해주세요."
    )

    @GetMapping("lists/{restAreaId}")
    public ResponseEntity<List<FacilityResponseDTO>> findByRestAreaId(@PathVariable("restAreaId") Integer restAreaId) {
        System.out.println("[FacilityCtrl] findByRestAreaId : " + restAreaId);
        
        List<FacilityResponseDTO> responses = facilityService.findByRestAreaId(restAreaId);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

    @Operation(
        summary = "원하는 편의시설이 있는 휴게소 조회",
        description = "원하는 편의시설을 입력해주세요."
    )

    @GetMapping("search/{names}")
    public ResponseEntity<List<RestAreaResponseDTO>> searchByNames(@RequestParam("names") List<String> names) {
        System.out.println("[FacilityCtrl] searchByNames : "+ names);
        
        return ResponseEntity.ok(facilityService.searchByNames(names));
    }
    
    
}
