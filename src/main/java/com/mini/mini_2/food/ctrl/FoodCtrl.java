package com.mini.mini_2.food.ctrl;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.food.domain.dto.FoodRequestDTO;
import com.mini.mini_2.food.domain.dto.FoodResponseDTO;
import com.mini.mini_2.food.service.FoodService;

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
            @PathVariable("foodId") Integer foodId,
            @RequestBody FoodRequestDTO request) {

        try {
            FoodResponseDTO response = foodService.update(foodId, request);
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    // 조회(정규표현식)
    @GetMapping("/search")
    public ResponseEntity<List<FoodResponseDTO>> searchFoods(@RequestParam("arg0") String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<FoodResponseDTO> result = foodService.searchFoodsByKeyword(keyword);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // 시그니처 유무
    @GetMapping("/signature/{restAreaId}")
    public ResponseEntity<List<FoodResponseDTO>> getSignatureFood(
            @PathVariable("restAreaId") Integer restAreaId) {

        List<FoodResponseDTO> result = foodService.getSignatureFood(restAreaId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    // 가격 필터
    @GetMapping("/search/price")
    public ResponseEntity<List<FoodResponseDTO>> searchFoodsByPrice(@RequestParam("arg0") double maxPrice) {
        List<FoodResponseDTO> result = foodService.searchFoodsByPrice(maxPrice);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }
}
