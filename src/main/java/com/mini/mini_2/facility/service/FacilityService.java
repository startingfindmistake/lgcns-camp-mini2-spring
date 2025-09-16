package com.mini.mini_2.facility.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.domain.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.facility.repository.FacilityRepository;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

import jakarta.transaction.Transactional;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;
    
    @Autowired
    private RestAreaRepository restAreaRepository;
   

    // Facility 생성
    @Transactional
    public FacilityResponseDTO create(FacilityRequestDTO request) {
        System.out.println("[FacilityService] create");

        Optional<RestAreaEntity> restArea = restAreaRepository.findById(request.getRestAreaId());

        if(restArea.isPresent()) {

            FacilityEntity facility = request.toEntity(restArea.get());
            return FacilityResponseDTO.fromEntity(facilityRepository.save(facility));
        }
        else {
            return null;
        }
    }
    
    // Facility 조회
    public List<FacilityResponseDTO> list(Integer restAreaId) {
        
        List<FacilityEntity> facilities = facilityRepository.findByRestArea_RestAreaId(restAreaId);
        
        return facilities.stream()
                         .map(entity -> FacilityResponseDTO.fromEntity(entity))
                         .toList();
        
    }

    // 원하는 Facility가 있는 휴게소 검색 
    public List<RestAreaResponseDTO> search(List<String> types) {

        // 중복 및 공백 제거
        List<String> cleandTypes = (types == null ? List.<String>of() : types)
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .distinct()
                .toList() ;
        
        if (cleandTypes.isEmpty()) return List.of() ;        
        return facilityRepository.findRestAreaByTypes(cleandTypes)
                .stream()
                .map(RestAreaResponseDTO::fromEntity)
                .toList() ;
    }
  
    
}
