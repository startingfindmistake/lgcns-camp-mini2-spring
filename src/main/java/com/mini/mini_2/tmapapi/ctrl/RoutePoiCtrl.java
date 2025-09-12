package com.mini.mini_2.tmapapi.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.tmapapi.service.RoutePoiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v2/mini/route/poi")
public class RoutePoiCtrl {
        
    @Autowired
    private RoutePoiService routeService;
    
    @GetMapping("route")
    public ResponseEntity<Void> route(@RequestParam String param) {
        
        
        
        return null;
    }
    
}
