package com.mini.mini_2.user.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.user.domain.dto.UserRequestDTO;
import com.mini.mini_2.user.domain.dto.UserResponseDTO;
import com.mini.mini_2.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/mini/user")
@Tag(name = "User API", description = "User API Documentation")
public class UserCtrl {
    
    @Autowired
    private UserService userService;
    
    @Operation(
        summary = "User Create",
        description = "Sign up"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Create User Success"),
            @ApiResponse(responseCode = "404",
                         description = "Create User Failed")
        }
    )
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
    
    @Operation(
        summary = "User Login",
        description = "Sign in"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Login Success")
        }
    )
    @PostMapping("signin")
    public ResponseEntity<UserResponseDTO> signin(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] signin : " + request);
        
        UserResponseDTO response = userService.signin(request);
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
