package com.mini.mini_2.food.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.food.domain.dto.FoodRequestDTO;
import com.mini.mini_2.food.domain.dto.FoodResponseDTO;
import com.mini.mini_2.food.service.FoodService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/mini/food")
@Tag(name = "Food API", description = "음식 API")
public class FoodCtrl {

    @Autowired
    private FoodService foodService;

    // Create
    @PostMapping("/create")
    public ResponseEntity<FoodResponseDTO> create(@RequestBody FoodRequestDTO request) {
        System.out.println("[FoodCtrl] POST create >>> " + request);
        try {
            FoodResponseDTO response = foodService.insert(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
        }
    }

    // 전체 조회
    @GetMapping("/foods")
    public ResponseEntity<List<FoodResponseDTO>> foods() {
        List<FoodResponseDTO> foods = foodService.list();
        return ResponseEntity.ok(foods);
    }

    // 일부 조회
    @GetMapping("/foods/{foodId}")
    public ResponseEntity<FoodResponseDTO> detail(@PathVariable("foodId") Integer foodId) {
        FoodResponseDTO response = foodService.get(foodId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(response);
    }

    // Update
    @PostMapping("/update/{foodId}")
    public ResponseEntity<FoodResponseDTO> update(
            @PathVariable("foodId") Integer id,
            @RequestBody FoodRequestDTO request) {

        try {
            FoodResponseDTO response = foodService.update(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete
    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<Void> delete(
            @PathVariable("foodId") Integer foodId) {

        foodService.delete(foodId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); }

}
