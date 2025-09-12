package com.mini.mini_2.tmapapi.ctrl;

import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.tmapapi.domain.dto.RouteRequestDTO;
import com.mini.mini_2.tmapapi.domain.dto.RouteResponseDTO;
import com.mini.mini_2.tmapapi.service.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v2/mini/route")
public class RouteCtrl {
    
    @Autowired
    private RouteService routeService;
    
    @PostMapping("route")
    public ResponseEntity<RouteResponseDTO> route(@RequestBody RouteRequestDTO request) {
        
        RouteResponseDTO response = routeService.route(request);
        
        System.out.println("[TMAP DEBUG] result : " + response);
        
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    } 
}
