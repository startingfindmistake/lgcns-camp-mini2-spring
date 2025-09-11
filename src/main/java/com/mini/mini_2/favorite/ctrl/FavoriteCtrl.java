package com.mini.mini_2.favorite.ctrl;

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


@RestController
@RequestMapping("/api/v1/mini/favorite")
@Tag(name = "Favorite API", description = "Favorite API Documentation")
public class FavoriteCtrl {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Operation(
        summary = "Favorite Post",
        description = "Favorite Post"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "Post Favorite Success"),
            @ApiResponse(responseCode = "404",
                         description = "Post Favorite Failed")
        }
    )
    @PostMapping("create")
    public ResponseEntity create(@RequestBody FavoriteRequestDTO request) {
        System.out.println("[FavoriteCtrl] create : " + request);
        FavoriteResponseDTO response = favoriteService.post(request);
        
        if(response != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
    }
    
    @DeleteMapping("delete/{favorite_id}")
    public ResponseEntity<Void> delete(@PathVariable("favorite_id") Integer favorite_id) {
        System.out.println("[FavoriteCtrl] delete : " + favorite_id);
        
        favoriteService.delete(favorite_id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    
}
