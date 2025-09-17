package com.mini.mini_2.openapi;

import com.mini.mini_2.auth.AuthInterceptor;
import com.mini.mini_2.openapi.domain.dto.FoodApiResponseDTO;
import com.mini.mini_2.openapi.service.FoodApiService;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.food.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OpenApiCtrlFood {

    @Autowired MockMvc mockMvc;

    @Autowired AuthInterceptor authInterceptor;
    @Autowired FoodApiService foodApiService;

    @Autowired RestAreaRepository restAreaRepository;
    @Autowired FoodRepository foodRepository;

    private Integer restAreaId;

    @BeforeEach
    void setup() throws Exception {
        // 인터셉터 통과
        Mockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        // 미리 휴게소 하나를 DB에 생성
        RestAreaEntity ra = RestAreaEntity.builder()
                .name("Sample RA")
                .direction("상행")
                .code("RA-FOOD-001")
                .build();
        restAreaId = restAreaRepository.save(ra).getRestAreaId();

        // 외부 Food API 스텁
        FoodApiResponseDTO resp = new FoodApiResponseDTO();
        FoodApiResponseDTO.FoodDTO dto = new FoodApiResponseDTO.FoodDTO();
        dto.setStdRestCd("RA-FOOD-001");
        dto.setFoodNm("우동");
        dto.setFoodCost("7000");
        dto.setBestfoodyn("Y");
        dto.setEtc("따뜻");
        resp.setList(Collections.singletonList(dto));
        Mockito.when(foodApiService.food(any())).thenReturn(resp);
    }

    @Test
    void food_update_saves_to_db() throws Exception {
        mockMvc.perform(get("/api/v1/mini/openapi/food_update"))
                .andExpect(status().isOk());

        // then: 음식이 저장되었는지 확인
        java.util.List<FoodEntity> foods = foodRepository.findAll();
        assertThat(foods).isNotEmpty();
        FoodEntity f = foods.get(0);
        assertThat(f.getRestArea().getRestAreaId()).isEqualTo(restAreaId);
        assertThat(f.getFoodName()).isEqualTo("우동");
        assertThat(f.getPrice()).isEqualTo("7000");
        assertThat(f.getIsSignature()).isEqualTo("Y");
        assertThat(f.getDescription()).isEqualTo("따뜻");
    }

    @TestConfiguration
    static class MockBeansConfig {
        @Bean @Primary AuthInterceptor authInterceptorMock() { return Mockito.mock(AuthInterceptor.class); }
        @Bean @Primary FoodApiService foodApiServiceMock() { return Mockito.mock(FoodApiService.class); }
    }
}
