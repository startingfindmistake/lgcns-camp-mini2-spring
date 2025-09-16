package com.mini.mini_2.tmapapi.ctrl;

import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.tmapapi.domain.dto.PoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.PoiResponseDTO;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RoutePoiResponseDTO;
import com.mini.mini_2.tmapapi.domain.dto.RouteRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RouteResponseDTO;
import com.mini.mini_2.tmapapi.service.PoiService;
import com.mini.mini_2.tmapapi.service.RoutePoiService;
import com.mini.mini_2.tmapapi.service.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v2/mini/tmap")
public class TmapApiCtrl {
    
    @Autowired
    private RouteService routeService;
    
    @Autowired
    private RoutePoiService routePoiService;
    
    @Autowired
    private PoiService poiService;
    
    @PostMapping("route")
    public ResponseEntity<RouteResponseDTO> route(@RequestBody RouteRequestDTO request) {
        
        RouteResponseDTO response = routeService.route(request);
        
        // System.out.println("[TMAP DEBUG] result : " + response);
        
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    } 

    @PostMapping("poi_of_route")
    public ResponseEntity<RoutePoiResponseDTO> poi_of_route(@RequestBody RoutePoiRequestDTO request) {
        // System.out.println("[TMAP DEBUG] request : " + request);
        
        RoutePoiResponseDTO response = routePoiService.poiOfRoute(request);
        
        // System.out.println("[TMAP DEBUG] result : " + response);
        
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } 
    
    @GetMapping("poi")
    public ResponseEntity<PoiResponseDTO> poi(PoiRequestDTO request) {
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
