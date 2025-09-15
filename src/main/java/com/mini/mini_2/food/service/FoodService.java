package com.mini.mini_2.food.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        FoodEntity existing = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식이 존재하지 않습니다. ID: " + foodId));

        RestAreaEntity fixedRestArea = existing.getRestArea();

        FoodEntity updated = FoodEntity.builder()
                .foodId(existing.getFoodId())
                .restArea(fixedRestArea)
                .foodName(request.getFoodName())
                .price(request.getPrice())
                .isSignature(request.getIsSignature())
                .build();

        FoodEntity saved = foodRepository.save(updated);

        return FoodResponseDTO.fromEntity(saved);
    }

    // Delete
    public boolean delete(Integer foodId) {

        FoodEntity food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식이 존재하지 않습니다. ID: " + foodId));

        foodRepository.delete(food);

        return true;
    }

    // 조회 (정규표현식)
   public List<FoodResponseDTO> searchFoodsByKeyword(String keyword) {
        Pattern pattern = Pattern.compile(Pattern.quote(keyword));

        return foodRepository.findAll().stream()
                .filter(f -> f.getFoodName() != null)
                .filter(f -> {
                    Matcher matcher = pattern.matcher(f.getFoodName());
                    return matcher.find(); 
                })
                .map(FoodResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    // 시그니쳐 유뮤
    public List<FoodResponseDTO> getSignatureFood(Integer restAreaId) {
        return foodRepository.findAll().stream()
                .filter(r -> r.getRestArea() != null && r.getRestArea().getRestAreaId().equals(restAreaId))
                .filter(f -> {String sign = f.getIsSignature();
                    return sign == "Y";})
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
    // 가격 필터
    public List<FoodResponseDTO> searchFoodsByPrice(double maxPrice) {
        return foodRepository.findAll().stream()
                .filter(f -> {double price = Double.parseDouble(f.getPrice());
                    return price <= maxPrice;})
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}


