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
import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiResponseDTO.RestAreaInfoDTO;
import com.mini.mini_2.openapi.service.FacilityApiService;
import com.mini.mini_2.openapi.service.FoodApiService;
import com.mini.mini_2.openapi.service.RestAreaInfoApiService;
import com.mini.mini_2.openapi.service.RestAreaLocationApiService;
import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.service.RestAreaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@RestController
@RequestMapping("api/v1/mini/openapi")
@Tag(name = "Open API", description = "외부 데이터 연동 및 관리 API")
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
    
    @Operation(
        summary = "휴게소 정보 업데이트",
        description = "외부 API로부터 휴게소 정보를 가져와 데이터베이스를 업데이트합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "RestArea Update Success"),
            @ApiResponse(responseCode = "500",
                         description = "RestArea Update Failed")
        }
    )
    @GetMapping("restarea_update")
    public ResponseEntity<Void> restarea_update(@ModelAttribute RestAreaInfoApiRequestDTO request) {
        RestAreaInfoApiResponseDTO responses = restAreaInfoApiService.info(request);
        // System.out.println("[UPDATE TEST] request : " + response);
        
        for (RestAreaInfoDTO restarea : responses.getList()) {
            String name = restarea.getSvarNm();
            String direction = restarea.getGudClssNm();
            String code = restarea.getSvarCd();
            String tel = restarea.getRprsTelNo();
            String address = restarea.getSvarAddr();
            String routeName = restarea.getRouteNm();
            
            // System.out.println("[RESTAREA] : " + restarea);
            RestAreaLocationApiRequestDTO requestForLocation = RestAreaLocationApiRequestDTO.builder()
                                                                                            .stdRestCd(restarea.getSvarCd())
                                                                                            .build();
            RestAreaLocationApiResponseDTO responseforLocation = restAreaLocationApiService.location(requestForLocation);

            // System.out.println("[RESTAREA LOCATION REPONSE] : " + responseforLocation);
            // System.out.println("[RESTAREA LOCATION REPONSE GET LIST] : " + responseforLocation.getList());
            // System.out.println("[RESTAREA LOCATION REPONSE GET COUNT] : " + responseforLocation.getCount());

            String xValue = null;
            String yValue = null;
            if (responseforLocation.getList() == null || responseforLocation.getList().isEmpty()) {
                continue;
                // xValue = responseforLocation.getList().get(0).getXValue();
                // yValue = responseforLocation.getList().get(0).getYValue();
            }
            xValue = responseforLocation.getList().get(0).getXValue();
            yValue = responseforLocation.getList().get(0).getYValue();

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
            restAreaService.create(restAreaRequest);
        }      
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
    @Operation(
        summary = "음식 정보 업데이트",
        description = "외부 API로부터 음식 정보를 가져와 데이터베이스를 업데이트합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Food Update Success"),
            @ApiResponse(responseCode = "500",
                         description = "Food Update Failed")
        }
    )
    @GetMapping("food_update")
    public ResponseEntity<Void> food_update(@ModelAttribute FoodApiRequestDTO request) {
        FoodApiResponseDTO responses = foodApiService.food(request);
        for (FoodDTO food : responses.getList()) {
            System.out.println("[DEBUG] food.getStdRestCd -> " + food.getStdRestCd());
            System.out.println("[DEBUG] food.getStdRestCd length -> " + food.getStdRestCd().length());
            RestAreaResponseDTO restarea = restAreaService.findByCode(food.getStdRestCd());
            
            if (restarea == null) continue;
            
            FoodRequestDTO foodRequest = FoodRequestDTO.builder()
                                                       .foodName(food.getFoodNm())
                                                       .price(food.getFoodCost())
                                                       .isSignature(food.getBestfoodyn())
                                                       .description(food.getEtc())
                                                       .restAreaId(restarea.getRestAreaId())
                                                       .build();
            System.out.println("[FOOD] : " + foodRequest);
            foodService.create(foodRequest);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
    @Operation(
        summary = "편의시설 정보 업데이트",
        description = "외부 API로부터 편의시설 정보를 가져와 데이터베이스를 업데이트합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Facility Update Success"),
            @ApiResponse(responseCode = "500",
                         description = "Facility Update Failed")
        }
    )
    @GetMapping("facility_update")
    public ResponseEntity<Void> facility_update(@ModelAttribute FacilityApiRequestDTO request) {
        FacilityApiResponseDTO responses = facilityApiService.facility(request);
        for (FacilityDTO facility : responses.getList()) {
            System.out.println("[DEBUG] facility.getStdRestCd -> " + facility.getStdRestCd());
            System.out.println("[DEBUG] facility.getStdRestCd length -> " + facility.getStdRestCd().length());
            RestAreaResponseDTO restarea = restAreaService.findByCode(facility.getStdRestCd());
            
            if(restarea == null) continue;
            
            FacilityRequestDTO facilityRequest = FacilityRequestDTO.builder()
                                                                   .name(facility.getPsName())
                                                                   .description(facility.getPsDesc())
                                                                   .restAreaId(restarea.getRestAreaId())
                                                                   .build();
            System.out.println("[FACILITY] : " + facilityRequest);
            facilityService.create(facilityRequest);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
    
    
    
    @Operation(
        summary = "휴게소 정보 조회",
        description = "외부 API에서 휴게소 기본 정보를 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "RestArea Info Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "RestArea Info Search Failed")
        }
    )
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
    
    @Operation(
        summary = "휴게소 위치 정보 조회",
        description = "외부 API에서 휴게소 위치 좌표 정보를 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "RestArea Location Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "RestArea Location Search Failed")
        }
    )
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
    
    @Operation(
        summary = "음식 정보 조회",
        description = "외부 API에서 휴게소별 음식 정보를 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Food Info Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "Food Info Search Failed")
        }
    )
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
    
    @Operation(
        summary = "편의시설 정보 조회",
        description = "외부 API에서 휴게소별 편의시설 정보를 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Facility Info Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "Facility Info Search Failed")
        }
    )
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
