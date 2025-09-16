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
import org.springframework.web.bind.annotation.PutMapping;
import com.mini.mini_2.auth.TokenService;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;




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
    public ResponseEntity<Void> signup(@RequestBody UserRequestDTO request) {
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
            // 로그인 성공 시 토큰 페어 발급
            String userId = response.getUserId().toString();
            String accessToken = tokenService.generateAccessToken(userId);
            String refreshToken = tokenService.generateRefreshToken(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of(
                            "user", response,
                            "accessToken", accessToken,
                            "refreshToken", refreshToken
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "invalid credentials"));
        }
    }

    @Operation(
        summary = "User Logout",
        description = "Invalidate token"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Logout Success")
        }
    )
    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenService.invalidateAccessToken(token);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Refresh Access Token",
        description = "Issue new access/refresh token pair by refresh token (rotation)"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200", description = "Refresh Success"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token")
        }
    )
    @PostMapping("refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "refreshToken is required"));
        }

        var pair = tokenService.refreshWithRotation(refreshToken);
        if (pair == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "invalid refresh token"));
        }
        return ResponseEntity.ok(Map.of(
                "accessToken", pair.accessToken(),
                "refreshToken", pair.refreshToken()
        ));
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
