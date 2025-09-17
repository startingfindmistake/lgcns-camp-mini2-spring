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
   

    // 편의시설 생성
    @Transactional
    public FacilityResponseDTO create(FacilityRequestDTO request) {
        System.out.println("[FacilityService] create : "+ request);

        Optional<RestAreaEntity> restArea = restAreaRepository.findById(request.getRestAreaId());

        if(restArea.isPresent()) {

            FacilityEntity facility = request.toEntity(restArea.get());
            return FacilityResponseDTO.fromEntity(facilityRepository.save(facility));
        }
        else {
            return null;
        }
    }
    
    // 휴게소 ID 기반 편의시설 조회
    public List<FacilityResponseDTO> findByRestAreaId(Integer restAreaId) {
        
        List<FacilityEntity> entities = facilityRepository.findByRestArea_RestAreaId(restAreaId);
        
        return entities.stream()
                         .map(entity -> FacilityResponseDTO.fromEntity(entity))
                         .toList();
        
    }

    // 원하는 편의시설이 있는 휴게소 조회
    public List<RestAreaResponseDTO> searchByNames(List<String> names) {

        
        List<String> cleandNames = (names == null ? List.<String>of() : names)
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .distinct()
                .toList() ;
        
        if (cleandNames.isEmpty()) return List.of() ;        
        return facilityRepository.findRestAreaByTypes(cleandNames)
                .stream()
                .map(RestAreaResponseDTO::fromEntity)
                .toList() ;
    }
  
    
}
