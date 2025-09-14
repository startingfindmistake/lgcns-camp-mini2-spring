package com.mini.mini_2.facility.service;


import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.domain.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.facility.repository.FacilityRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

import jakarta.transaction.Transactional;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private RestAreaRepository restAreaRepository;
   

    // POST 시설 생성
    @Transactional
    public FacilityResponseDTO post(FacilityRequestDTO request) {
        System.out.println("[FacilityService] post ");

        // rest_area 있는지 확인
        RestAreaEntity restArea = restAreaRepository.findById(request.getRestAreaId())
                .orElseThrow(() -> new RuntimeException("휴게소가 존재하지 않습니다. ID= " + request.getRestAreaId()));

        // facilityEntity에 FK       
        FacilityEntity facility = FacilityEntity.builder()
                .restArea(restArea)
                .name(request.getName())
                .description(request.getDescription())   // description 추가
                .build();

        FacilityEntity entity = facilityRepository.save(facility);
        return FacilityResponseDTO.fromEntity(entity);
           
        
    }
    
    public List<FacilityResponseDTO> find(Integer restAreaId) {
        
        List<FacilityEntity> facilities = facilityRepository.findByRestArea_RestAreaId(restAreaId);
        
        return facilities.stream()
                         .map(entity -> FacilityResponseDTO.fromEntity(entity))
                         .toList();
        
    }

    // [추가 - 필터링 작업] 주어진 편의시설을 갖춘 휴게소 ID 반환
    @Transactional
    public List<Integer> findRestIdByType(List<String> types) {

        // 시설 공백이나 중복 필터
        List<String> cleandTypes = (types == null ? List.<String>of() : types)
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .distinct()
                .toList() ;

        if (cleandTypes.isEmpty()) return List.of() ;        
        return facilityRepository.findRestAreaIdsByTypes(cleandTypes) ;
    }


    
    
    
}
