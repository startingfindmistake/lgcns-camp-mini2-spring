package com.mini.mini_2.openapi;

import com.mini.mini_2.auth.AuthInterceptor;
import com.mini.mini_2.openapi.domain.dto.FacilityApiResponseDTO;
import com.mini.mini_2.openapi.service.FacilityApiService;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.facility.repository.FacilityRepository;
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
class OpenApiCtrlFacility {

    @Autowired MockMvc mockMvc;

    @Autowired AuthInterceptor authInterceptor;
    @Autowired FacilityApiService facilityApiService;

    @Autowired RestAreaRepository restAreaRepository;
    @Autowired FacilityRepository facilityRepository;

    private Integer restAreaId;

    @BeforeEach
    void setup() throws Exception {
        // 인터셉터 통과
        Mockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        // 미리 휴게소 하나를 DB에 생성
        RestAreaEntity ra = RestAreaEntity.builder()
                .name("Sample RA")
                .direction("하행")
                .code("RA-FAC-001")
                .build();
        restAreaId = restAreaRepository.save(ra).getRestAreaId();

        // 외부 Facility API 스텁
        FacilityApiResponseDTO resp = new FacilityApiResponseDTO();
        FacilityApiResponseDTO.FacilityDTO dto = new FacilityApiResponseDTO.FacilityDTO();
        dto.setStdRestCd("RA-FAC-001");
        dto.setPsName("편의점");
        dto.setPsDesc("24시간");
        resp.setList(Collections.singletonList(dto));
        Mockito.when(facilityApiService.facility(any())).thenReturn(resp);
    }

    @Test
    void facility_update_saves_to_db() throws Exception {
        mockMvc.perform(get("/api/v1/mini/openapi/facility_update"))
                .andExpect(status().isOk());

        // then: 편의시설이 저장되었는지 확인
        java.util.List<FacilityEntity> list = facilityRepository.findAll();
        assertThat(list).isNotEmpty();
        FacilityEntity f = list.get(0);
        assertThat(f.getRestArea().getRestAreaId()).isEqualTo(restAreaId);
        assertThat(f.getName()).isEqualTo("편의점");
        assertThat(f.getDescription()).isEqualTo("24시간");
    }

    @TestConfiguration
    static class MockBeansConfig {
        @Bean @Primary AuthInterceptor authInterceptorMock() { return Mockito.mock(AuthInterceptor.class); }
        @Bean @Primary FacilityApiService facilityApiServiceMock() { return Mockito.mock(FacilityApiService.class); }
    }
}
