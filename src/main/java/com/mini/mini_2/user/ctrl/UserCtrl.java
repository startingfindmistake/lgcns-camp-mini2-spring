package com.mini.mini_2.user.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.user.domain.dto.UserRequestDTO;
import com.mini.mini_2.user.domain.dto.UserResponseDTO;
import com.mini.mini_2.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/mini/user")
public class UserCtrl {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("signup")
    public ResponseEntity signup(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] signup : " + request);
        UserResponseDTO response = userService.signup(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }
    
}
