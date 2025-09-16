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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/mini/food")
@Tag(name = "Food API", description = "음식 API")
public class FoodCtrl {

    @Autowired
    private FoodService foodService;

    @Operation(
        summary = "휴게소 음식 생성",
        description = "휴게소 음식을 생성해주세요."
    )

    @PostMapping("/create")
    public ResponseEntity<FoodResponseDTO> create(@RequestBody FoodRequestDTO request) {
        System.out.println("[FoodCtrl] create : " + request);
        try {
            FoodResponseDTO response = foodService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
        summary = "음식 목록 전체 조회",
        description = "휴게소 음식 전체 목록입니다."
    )

    @GetMapping("/lists")
    public ResponseEntity<List<FoodResponseDTO>> findAll() {
        List<FoodResponseDTO> responses = foodService.findAll();
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "음식 ID 기반 음식 조회",
        description = "음식 ID를 입력해주세요."
    )

    @GetMapping("/lists/{foodId}")
    public ResponseEntity<FoodResponseDTO> findByFoodId(@PathVariable("foodId") Integer foodId) {
        FoodResponseDTO response = foodService.findByFoodId(foodId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "음식 정보 수정",
        description = "음식 ID를 입력해주세요."
    )

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

    @Operation(
        summary = "음식 정보 삭제",
        description = "음식 ID를 입력해주세요."
    )
    
    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<Void> delete(
            @PathVariable("foodId") Integer foodId) {

        foodService.delete(foodId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
        summary = "메뉴 기반 음식 목록 조회",
        description = "음식 메뉴를 입력해주세요."
    )
    
    @GetMapping("/search/{name}")
    public ResponseEntity<List<FoodResponseDTO>> searchByName(@RequestParam("arg0") String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<FoodResponseDTO> responses = foodService.searchByName(keyword);

        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responses);
        }
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "휴게소 ID 기반 대표 메뉴 목록 조회",
        description = "휴게소 ID를 입력해주세요."
    )

    @GetMapping("/search/signature/{restAreaId}")
    public ResponseEntity<List<FoodResponseDTO>> searchByRestAreaId(
            @PathVariable("restAreaId") Integer restAreaId) {

        List<FoodResponseDTO> responses = foodService.searchByRestAreaId(restAreaId);

        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responses);
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "가격 기반 음식 목록 조회",
        description = "가격을 입력해주세요."
    )

    @GetMapping("/search/{price}")
    public ResponseEntity<List<FoodResponseDTO>> searchByPrice(@RequestParam("arg0") double maxPrice) {
        List<FoodResponseDTO> responses = foodService.searchByPrice(maxPrice);

        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responses);
        }

        return ResponseEntity.ok(responses);
    }
}
