package com.mini.mini_2.favorite.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.favorite.domain.dto.FavoriteRequestDTO;
import com.mini.mini_2.favorite.domain.dto.FavoriteResponseDTO;
import com.mini.mini_2.favorite.service.FavoriteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/mini/favorite")
@Tag(name = "Favorite API", description = "즐겨찾기 API")
public class FavoriteCtrl {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Operation(
        summary = "즐겨찾기 생성",
        description = "즐겨찾기 추가해주세요."
    )

    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Post Favorite Success"),
            @ApiResponse(responseCode = "404",
                         description = "Post Favorite Failed")
        }
    )

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody FavoriteRequestDTO request) {
        System.out.println("[FavoriteCtrl] create : " + request);
        FavoriteResponseDTO response = favoriteService.create(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }

    @Operation(
        summary = "즐겨찾기 삭제",
        description = "즐겨찾기 ID를 입력해주세요."
    )
    
    @DeleteMapping("delete/{favoriteId}")
    public ResponseEntity<Void> delete(@PathVariable("favoriteId") Integer favoriteId) {
        System.out.println("[FavoriteCtrl] delete : " + favoriteId);
        
        favoriteService.delete(favoriteId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
        summary = "회원 ID 기반 즐겨찾기 목록 조회",
        description = "회원 ID를 입력해주세요."
    )
    
    @GetMapping("lists/{userId}")
    public ResponseEntity<List<FavoriteResponseDTO>> findByUserId(@PathVariable("userId") Integer userId) {
        System.out.println("[FavoriteCtrl] get favorite by user : " + userId);
        
        List<FavoriteResponseDTO> responses = favoriteService.findByUserId(userId);
        
        if (responses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    
    
}
