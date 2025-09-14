package com.mini.mini_2.openapi.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.service.FacilityService;
import com.mini.mini_2.food.domain.dto.FoodRequestDTO;
import com.mini.mini_2.food.service.FoodService;
import com.mini.mini_2.openapi.domain.dto.FacilityApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.FacilityApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.FoodApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.FacilityApiResponseDTO.FacilityDTO;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO.FoodDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiResponseDTO.RestAreaLocationDTO;
import com.mini.mini_2.openapi.service.FacilityApiService;
import com.mini.mini_2.openapi.service.FoodApiService;
import com.mini.mini_2.openapi.service.RestAreaInfoApiService;
import com.mini.mini_2.openapi.service.RestAreaLocationApiService;
import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.service.RestAreaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2/mini/openapi")
public class OpenApiCtrl {
    
    @Autowired
    private FoodApiService foodApiService;
    @Autowired
    private RestAreaLocationApiService restAreaLocationApiService;
    @Autowired
    private FacilityApiService facilityApiService;
    @Autowired
    public RestAreaInfoApiService restAreaInfoApiService;
    
    @Autowired
    public RestAreaService restAreaService;
    @Autowired
    public FoodService foodService;
    @Autowired
    public FacilityService facilityService;
    
    @GetMapping("restarea_update")
    public ResponseEntity<Void> restarea_update(@ModelAttribute RestAreaLocationApiRequestDTO request) {
        RestAreaLocationApiResponseDTO responses = restAreaLocationApiService.location(request);
        // System.out.println("[UPDATE TEST] request : " + response);
        
        for (RestAreaLocationDTO restarea : responses.getList()) {
            String xValue = restarea.getXValue();
            String yValue = restarea.getYValue();
            
            // System.out.println("[RESTAREA] : " + restarea);
            RestAreaInfoApiRequestDTO requestForInfo = RestAreaInfoApiRequestDTO.builder()
                                                                                .svarCd(restarea.getStdRestCd())
                                                                                .build();
            RestAreaInfoApiResponseDTO responseforInfo = restAreaInfoApiService.info(requestForInfo);

            // System.out.println("[RESTAREA LOCATION REPONSE] : " + responseforLocation);
            // System.out.println("[RESTAREA LOCATION REPONSE GET LIST] : " + responseforLocation.getList());
            // System.out.println("[RESTAREA LOCATION REPONSE GET COUNT] : " + responseforLocation.getCount());


            // if (responseforLocation == null || responseforLocation.getList() == null || responseforLocation.getList().isEmpty()) {
            //     continue;
            // }

            String name = responseforInfo.getList().get(0).getSvarNm();
            String direction = responseforInfo.getList().get(0).getGudClssNm();
            String code = responseforInfo.getList().get(0).getSvarCd();
            String tel = responseforInfo.getList().get(0).getRprsTelNo();
            String address = responseforInfo.getList().get(0).getSvarAddr();
            String routeName = responseforInfo.getList().get(0).getRouteNm();

            RestAreaRequestDTO restAreaRequest = RestAreaRequestDTO.builder()
                    .name(name)
                    .direction(direction)
                    .code(code)
                    .tel(tel)
                    .address(address)
                    .routeName(routeName)
                    .xValue(xValue)
                    .yValue(yValue)
                    .build();
            restAreaService.insert(restAreaRequest);
        }      
        return null;
    }
    
    @GetMapping("food_update")
    public ResponseEntity<Void> food_update(@ModelAttribute FoodApiRequestDTO request) {
        FoodApiResponseDTO responses = foodApiService.food(request);
        for (FoodDTO food : responses.getList()) {
            RestAreaResponseDTO restarea = restAreaService.findByCode(food.getStdRestCd());
            
            FoodRequestDTO foodRequest = FoodRequestDTO.builder()
                                                       .foodName(food.getFoodNm())
                                                       .price(food.getFoodCost())
                                                       .isSignature(food.getBestfoodyn())
                                                       .description(food.getEtc())
                                                       .restAreaId(restarea.getRestAreaId())
                                                       .build();
            foodService.insert(foodRequest);
        }
        
        return null;
    }
    
    @GetMapping("facility_update")
    public ResponseEntity<Void> facility_update(@ModelAttribute FacilityApiRequestDTO request) {
        FacilityApiResponseDTO responses = facilityApiService.facility(request);
        for (FacilityDTO facility : responses.getList()) {
            RestAreaResponseDTO restarea = restAreaService.findByCode(facility.getStdRestCd());
            
            FacilityRequestDTO facilityRequest = FacilityRequestDTO.builder()
                                                                   .name(facility.getPsName())
                                                                   .description(facility.getPsDesc())
                                                                   .restAreaId(restarea.getRestAreaId())
                                                                   .build();
            System.out.println("[FACILITY] : " + facilityRequest);
            System.out.println("[FACILITY DESC] : " + facilityRequest.getDescription());
            facilityService.post(facilityRequest);
        }
        
        return null;
    }
    
    
    
    
    @GetMapping("info")
    public ResponseEntity<RestAreaInfoApiResponseDTO> restarea(@ModelAttribute RestAreaInfoApiRequestDTO request) {
        RestAreaInfoApiResponseDTO response = restAreaInfoApiService.info(request);
        
        System.out.println("[OPENAPI INFO] result : " + response);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
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
