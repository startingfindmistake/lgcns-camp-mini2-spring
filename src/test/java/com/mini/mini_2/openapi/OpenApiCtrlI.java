package com.mini.mini_2.openapi;

import com.mini.mini_2.auth.AuthInterceptor;
import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiResponseDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaLocationApiResponseDTO;
import com.mini.mini_2.openapi.service.RestAreaInfoApiService;
import com.mini.mini_2.openapi.service.RestAreaLocationApiService;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.Rollback;
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
@Rollback(true)
class OpenApiCtrlI {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestAreaRepository restAreaRepository;

    @Autowired
    private AuthInterceptor authInterceptor; // 인터셉터 통과 처리

    @Autowired
    private RestAreaInfoApiService restAreaInfoApiService; // 외부 API 스텁

    @Autowired
    private RestAreaLocationApiService restAreaLocationApiService; // 외부 API 스텁

    private static final String SAMPLE_CODE = "TEST";

    @BeforeEach
    void setup() throws Exception {
        // 인증 인터셉터는 항상 통과
        Mockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        // 외부 API 스텁: 휴게소 기본 정보 1건
        RestAreaInfoApiResponseDTO infoResp = new RestAreaInfoApiResponseDTO();
        RestAreaInfoApiResponseDTO.RestAreaInfoDTO info = new RestAreaInfoApiResponseDTO.RestAreaInfoDTO();
        info.setSvarNm("Sample Rest Area");
        info.setGudClssNm("상행");
        info.setSvarCd(SAMPLE_CODE);
        info.setRprsTelNo("010-1234-5678");
        info.setSvarAddr("서울시 어딘가 123");
        info.setRouteNm("경부고속도로");
        infoResp.setList(Collections.singletonList(info));
        Mockito.when(restAreaInfoApiService.info(any())).thenReturn(infoResp);

        // 외부 API 스텁: 좌표 1건
        RestAreaLocationApiResponseDTO locResp = new RestAreaLocationApiResponseDTO();
        RestAreaLocationApiResponseDTO.RestAreaLocationDTO loc = new RestAreaLocationApiResponseDTO.RestAreaLocationDTO();
        loc.setXValue("127.000");
        loc.setYValue("37.500");
        locResp.setList(Collections.singletonList(loc));
        Mockito.when(restAreaLocationApiService.location(any())).thenReturn(locResp);
    }

    @Test
    void restarea_update_saves_to_db() throws Exception {
        // when: 업데이트 호출
        mockMvc.perform(get("/api/v1/mini/openapi/restarea_update"))
               .andExpect(status().isOk());

        // then: DB에 해당 코드로 저장되었는지 확인
        RestAreaEntity saved = restAreaRepository.findByCode(SAMPLE_CODE).orElse(null);
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Sample Rest Area");
        assertThat(saved.getDirection()).isEqualTo("상행");
        assertThat(saved.getTel()).isEqualTo("010-1234-5678");
        assertThat(saved.getAddress()).isEqualTo("서울시 어딘가 123");
        assertThat(saved.getRouteName()).isEqualTo("경부고속도로");
        assertThat(saved.getXValue()).isEqualTo("127.000");
        assertThat(saved.getYValue()).isEqualTo("37.500");
    }

    @TestConfiguration
    static class MockBeansConfig {
        @Bean
        @Primary
        AuthInterceptor authInterceptorMock() {
            return Mockito.mock(AuthInterceptor.class);
        }

        @Bean
        @Primary
        RestAreaInfoApiService restAreaInfoApiServiceMock() {
            return Mockito.mock(RestAreaInfoApiService.class);
        }

        @Bean
        @Primary
        RestAreaLocationApiService restAreaLocationApiServiceMock() {
            return Mockito.mock(RestAreaLocationApiService.class);
        }
    }
}
