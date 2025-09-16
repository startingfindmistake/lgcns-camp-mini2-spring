package com.mini.mini_2.food.service;

import java.util.List;
import java.util.Optional;
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

    // 음식 생성
    public FoodResponseDTO create(FoodRequestDTO request) {
        System.out.println("[FoodService] create : " + request); 

        RestAreaEntity restAreaEntity = restAreaRepository.findById(request.getRestAreaId()).get();
        
        FoodEntity foodEntity = request.toEntity(restAreaEntity);

        foodRepository.save(foodEntity);

        return FoodResponseDTO.fromEntity(foodEntity);
    }

    // 전체 조회
    public List<FoodResponseDTO> findAll() {
        return foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 일부 조회
    public FoodResponseDTO findByFoodId(Integer foodId) {
        return foodRepository.findById(foodId)
                .map(FoodResponseDTO::fromEntity)
                .orElse(null);
    }

    // 메뉴 수정
    public FoodResponseDTO update(Integer foodId, FoodRequestDTO request) {

        Optional<FoodEntity> foodEntity = foodRepository.findById(foodId);

        // RestAreaEntity fixedRestArea = existing.getRestArea();

        FoodEntity entity = foodEntity.get();
        entity.setFoodName(request.getFoodName());
        entity.setIsSignature(request.getIsSignature());
        entity.setPrice(request.getPrice());
        entity.setDescription(request.getDescription());

        FoodEntity saved = foodRepository.save(entity);

        return FoodResponseDTO.fromEntity(saved);
    }

    // 메뉴 삭제
    public boolean delete(Integer foodId) {

        FoodEntity entity = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식이 존재하지 않습니다. ID: " + foodId));

        foodRepository.delete(entity);

        return true;
    }

    // 메뉴 필터를 통한 음식 조회
   public List<FoodResponseDTO> searchByName(String keyword) {
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

    // 대표 메뉴 필터를 통한 음식 조회
    public List<FoodResponseDTO> searchByRestAreaId(Integer restAreaId) {
        return foodRepository.findAll().stream()
                .filter(r -> r.getRestArea() != null && r.getRestArea().getRestAreaId().equals(restAreaId))
                .filter(f -> {String sign = f.getIsSignature();
                    return "Y".equals(sign);})
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 가격 필터를 통한 음식 조회
    public List<FoodResponseDTO> searchByPrice(double maxPrice) {
        return foodRepository.findAll().stream()
                .filter(f -> {double price = Double.parseDouble(f.getPrice());
                    return price <= maxPrice;})
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}


