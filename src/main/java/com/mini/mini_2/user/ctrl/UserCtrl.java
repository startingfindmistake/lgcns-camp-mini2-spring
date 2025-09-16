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



@RestController
@RequestMapping("/api/v1/mini/user")
@Tag(name = "User API", description = "회원 API")
public class UserCtrl {
    
    @Autowired
    private UserService userService;
    
    @Operation(
        summary = "회원 생성",
        description = "계정을 생성해주세요."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Create User Success"),
            @ApiResponse(responseCode = "404",
                         description = "Create User Failed")
        }
    )

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] create : "+ request);
        UserResponseDTO response = userService.create(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @Operation(
        summary = "회원 로그인",
        description = "계정을 로그인해주세요."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Login Success")
        }
    )

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] login : "+ request);
        
        UserResponseDTO response = userService.login(request);
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
        summary = "회원 정보 수정",
        description = "수정할 회원 정보를 입력해주세요."
    )
    
    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO request) {
        System.out.println("[UserCtrl] update : " + request);

        UserResponseDTO response = userService.update(request);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
        summary = "회원 정보 삭제",
        description = "삭제할 회원 정보를 입력해주세요."
    )
    
    @DeleteMapping("/delete")
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
