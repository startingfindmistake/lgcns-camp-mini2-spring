package com.mini.mini_2.all;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.domain.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.service.FacilityService;
import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.rest_area.service.RestAreaService;



@SpringBootTest
@Transactional
@Rollback
public class FacilityTest {

    @Autowired 
    RestAreaRepository  restAreaRepository;
    @Autowired
    RestAreaService     restAreaService;
    @Autowired
    FacilityService     facilityService;

    /*
     * 휴게소 2곳 생성 후
     * - A(덕평): 수유실(CONVENIENCE), 전기차충전(EV_CHARGER)
     * - B(여주): 수면실(REST_ROOM)만
     * 유형 검색(type=CONVENIENCE) 시 A만 걸리는지 검증
    */


    @Test
    // 1. 휴게소 생성
    public void test() {
        // 1번 휴게소
        RestAreaRequestDTO rest1 = RestAreaRequestDTO.builder()
                .code("A012")            
                .name("금강휴게소")
                .direction("상행")
                .tel("043-111-2222")
                .address("충북 어딘가")
                .routeName("경부고속도로")
                .build();
        RestAreaResponseDTO response1 = restAreaService.create(rest1);
        assertNotNull(response1.getRestAreaId());        
        


        // 2번 휴게소
        RestAreaRequestDTO rest2 = RestAreaRequestDTO.builder()
                    .name("횡성휴게소")
                    .direction("하행")
                    .code("A014")
                    .tel("033-121-2234")
                    .address("강원도 어딘가")
                    .routeName("영동고속도로")
                    .build();
        RestAreaResponseDTO response2 = restAreaService.create(rest2);
        assertNotNull(response2.getRestAreaId());              
      


        // 2. 시설 생성
        // 금강: 수유실
        FacilityRequestDTO facility1 = FacilityRequestDTO.builder()
                .restAreaId(response1.getRestAreaId())
                .name("수유실")
                .description("24시간")
                .build();
        FacilityResponseDTO faresponse1 = facilityService.create(facility1);
        assertNotNull(faresponse1.getFacilityId());

        // 금강: 전기차충전
        FacilityRequestDTO facility2 = FacilityRequestDTO.builder()
                .restAreaId(response1.getRestAreaId())
                .name("전기차충전소")
                .description("급속 2기")
                .build();
        FacilityResponseDTO faresponse2 = facilityService.create(facility2);
        assertNotNull(faresponse2.getFacilityId());

        // 여주
        FacilityRequestDTO facility3 = FacilityRequestDTO.builder()
                .restAreaId(response2.getRestAreaId())
                .name("약국")   
                .description("24시간")
                .build();
    
        FacilityResponseDTO faresponse3 = facilityService.create(facility3);
        assertNotNull(faresponse3.getFacilityId());

        // 시설명 필터: "수유실" 보유 휴게소만
        List<RestAreaResponseDTO> onlyType = facilityService.search(Arrays.asList("수유실"));
        assertFalse(onlyType.isEmpty(), "'수유실' 보유 휴게소 검색");

        Set<String> onlyNames = onlyType.stream()
                        .map(RestAreaResponseDTO::getName)   
                        .collect(Collectors.toSet());

        // 덕평은 수유실 보유
        assertTrue(onlyNames.contains("금강휴게소"), "금강휴게소는 수유실 포함");
        // 여주는 수유실 없음
        assertFalse(onlyNames.contains("횡성휴게소"), "횡성휴게소는 수유실 없음");


        // 다중 필터: 수유실과 전기차충전소 모두 갖춘 휴게소
        List<RestAreaResponseDTO> both = facilityService.search(
                Arrays.asList("수유실", "전기차충전소"));
        Set<String> bothNames = both.stream()
                        .map(RestAreaResponseDTO::getName)
                        .collect(Collectors.toSet());

        assertTrue(bothNames.contains("금강휴게소"), "금강휴게소는 두 시설 모두 있어서 포함");
        assertFalse(bothNames.contains("횡성휴게소"), "횡성휴게소는 둘 다 없으므로 제외");
    
    }



    
}
