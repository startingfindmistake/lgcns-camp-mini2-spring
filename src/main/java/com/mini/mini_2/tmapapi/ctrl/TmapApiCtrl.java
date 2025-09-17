package com.mini.mini_2.tmapapi.ctrl;

import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.tmapapi.domain.dto.PoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.PoiResponseDTO;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RouteRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RouteResponseDTO;
import com.mini.mini_2.tmapapi.service.PoiService;
import com.mini.mini_2.tmapapi.service.RoutePoiService;
import com.mini.mini_2.tmapapi.service.RouteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/mini/tmap")
@Tag(name = "TMAP API", description = "TMAP 경로 및 POI 검색 API")
public class TmapApiCtrl {
    
    @Autowired
    private RouteService routeService;
    
    @Autowired
    private RoutePoiService routePoiService;
    
    @Autowired
    private PoiService poiService;
    
    @Operation(
        summary = "경로 검색",
        description = "출발지와 목적지를 입력하여 경로를 검색합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Route Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "Route Search Failed")
        }
    )
    @PostMapping("route")
    public ResponseEntity<RouteResponseDTO> route(@RequestBody RouteRequestDTO request) {
        System.out.println("[TMAP ROUTE] request");
        // System.out.println("[TMAP ROUTE] request : " + request);
        
        RouteResponseDTO response = routeService.route(request);
        
        // System.out.println("[TMAP ROUTE] result : " + response);
        
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    } 

    @Operation(
        summary = "경로 상의 휴게소 검색",
        description = "경로를 따라 이동하며 근처의 휴게소를 검색합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "POI of Route Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "POI of Route Search Failed")
        }
    )
    @PostMapping("poi_of_route")
    public ResponseEntity<List<RestAreaResponseDTO>> poi_of_route(@RequestBody RoutePoiRequestDTO request) {
        System.out.println("[TMAP POI OF ROUTE] request ");
        // System.out.println("[TMAP POI OF ROUTE] request : " + request);
        
        List<RestAreaResponseDTO> responses = routePoiService.poiOfRoute(request);
        
        // System.out.println("[TMAP POI OF ROUTE] result : " + responses);
        
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } 
    
    @Operation(
        summary = "POI 검색",
        description = "위치 정보를 이용하여 관심 지점(POI)을 검색합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "POI Search Success"),
            @ApiResponse(responseCode = "500",
                         description = "POI Search Failed")
        }
    )
    @GetMapping("poi")
    public ResponseEntity<PoiResponseDTO> poi(PoiRequestDTO request) {
        System.out.println("[TMAP POI] request ");
        // System.out.println("[TMAP POI] request : " + request);
        
        PoiResponseDTO response = poiService.poi(request);
        
        // System.out.println("[TMAP POI] result : " + response);
        
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } 
}
