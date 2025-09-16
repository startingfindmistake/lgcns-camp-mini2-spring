package com.mini.mini_2.all;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.food.domain.dto.FoodRequestDTO;
import com.mini.mini_2.food.domain.dto.FoodResponseDTO;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.food.repository.FoodRepository;
import com.mini.mini_2.food.service.FoodService;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

@SpringBootTest
@Transactional
public class FoodTest {
    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestAreaRepository restAreaRepository;

    @Test
    public void test(){
        RestAreaEntity restArea = RestAreaEntity.builder()
                .name("용인휴게소")
                .direction("상행")
                .code("0050")
                .tel("031-000-0000")
                .address("경기도")
                .routeName("00고속도로")
                .build();

        restArea = restAreaRepository.save(restArea);

        // CREATE
        FoodRequestDTO foodRequest = FoodRequestDTO.builder()
                .restAreaId(restArea.getRestAreaId())
                .foodName("치즈돈가스")
                .price("8000")
                .isSignature("Y")
                .build();

        FoodResponseDTO foodResponse = foodService.create(foodRequest);
        assertNotNull(foodResponse.getFoodId());
        assertEquals("치즈돈가스", foodResponse.getFoodName());

        // READ
        FoodResponseDTO foundFood = foodService.findByFoodId(foodResponse.getFoodId());
        assertNotNull(foundFood);
        assertEquals("치즈돈가스", foundFood.getFoodName());

        // UPDATE
        FoodRequestDTO updateRequest = FoodRequestDTO.builder()
                .restAreaId(restArea.getRestAreaId())
                .foodName("김치돈가스")
                .price("8500")
                .isSignature("Y")
                .build();

        FoodResponseDTO updateFood = foodService.update(foodResponse.getFoodId(), updateRequest);
        assertEquals("김치돈가스", updateFood.getFoodName());
        assertEquals("8500", updateFood.getPrice());

        // DELETE
        boolean delete = foodService.delete(foodResponse.getFoodId());
        assertTrue(delete);

        Optional<FoodEntity> deleteEntity = foodRepository.findById(foodResponse.getFoodId());
        assertFalse(deleteEntity.isPresent());
    }
    
    
    @Test
    public void testSignatureFood() {
        List<FoodResponseDTO> signatureFoods = foodService.getSignatureFood(281);
        System.out.println("시그니처 음식 목록: " + signatureFoods);
    }

    @Test
    public void testPriceFilter() {
        List<FoodResponseDTO> cheapFoods = foodService.searchFoodsByPrice(8000);
        System.out.println("8000원 이하 음식 목록: " + cheapFoods);
    }
    
    @Test
    public void foodFilter(){
        List<FoodResponseDTO> result = foodService.searchFoodsByKeyword("돈가스");
        System.out.println("검색 결과: " + result);
    }
}
