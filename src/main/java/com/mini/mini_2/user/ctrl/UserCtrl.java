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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.mini.mini_2.auth.TokenService;
import java.util.Map;




@RestController
@RequestMapping("/api/v1/mini/user")
@Tag(name = "User API", description = "User API Documentation")
public class UserCtrl {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenService tokenService;
    
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
    public ResponseEntity<?> signin(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] signin : " + request);
        UserResponseDTO response = userService.signin(request);
        if (response != null) {
            // 로그인 성공 시 JWT 토큰 발급
            String token = tokenService.generateToken(response.getUserId().toString());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("user", response, "token", token));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping("update_password")
    public ResponseEntity<UserResponseDTO> update_password(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] update password : " + request);

        UserResponseDTO response = userService.updatePassword(request);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping("delete")
    public ResponseEntity<Void> delete(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] delete : " + request);
        
        Integer result = userService.delete(request);

        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
