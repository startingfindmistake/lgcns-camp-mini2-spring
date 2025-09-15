package com.mini.mini_2.food.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.food.domain.dto.FoodRequestDTO;
import com.mini.mini_2.food.domain.dto.FoodResponseDTO;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.food.repository.FoodRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;



@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestAreaRepository restAreaRepository;

    // Create
    public FoodResponseDTO insert(FoodRequestDTO request) {

        RestAreaEntity restArea = restAreaRepository.findById(request.getRestAreaId()).get();
        
        FoodEntity food = request.toEntity(restArea);

        foodRepository.save(food);

        return FoodResponseDTO.fromEntity(food);
    }

    // 전체 조회
    public List<FoodResponseDTO> list() {
        return foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 일부 조회
    public FoodResponseDTO get(Integer foodId) {
        return foodRepository.findById(foodId)
                .map(FoodResponseDTO::fromEntity)
                .orElse(null);
    }

    // Update
    public FoodResponseDTO update(Integer foodId, FoodRequestDTO request) {

        Optional<FoodEntity> existing = foodRepository.findById(foodId);

        // RestAreaEntity fixedRestArea = existing.getRestArea();

        FoodEntity existFood = existing.get();
        existFood.setFoodName(request.getFoodName());
        existFood.setIsSignature(request.getIsSignature());
        existFood.setPrice(request.getPrice());
        existFood.setDescription(request.getDescription());

        FoodEntity saved = foodRepository.save(existFood);

        return FoodResponseDTO.fromEntity(saved);
    }

    // Delete
    public boolean delete(Integer foodId) {

        FoodEntity food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식이 존재하지 않습니다. ID: " + foodId));

        foodRepository.delete(food);

        return true;
    }
}


