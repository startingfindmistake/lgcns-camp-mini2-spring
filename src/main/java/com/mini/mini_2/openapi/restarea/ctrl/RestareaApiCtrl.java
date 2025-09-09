package com.mini.mini_2.openapi.restarea.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openapi.restarea.dto.entity.RestareaEntity;
import com.mini.mini_2.openapi.restarea.service.RestareaApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/mini/restarea-api")
public class RestareaApiCtrl {
    
    @Autowired
    private RestareaApiService restareaApiService;
    
    @GetMapping("get")
    public ResponseEntity get() {
        
        RestareaEntity response = restareaApiService.get();
        
        System.out.println("openapi test get : " + response);
        
        return ResponseEntity.status(HttpStatus.OK)
                             .body(response);
    }
    
}
