package com.mini.mini_2.rest_area.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
public class RestAreaService {

    @Autowired
    private RestAreaRepository restRepository ;
    
    // INSERT 생성(등록)
    public RestAreaResponseDTO insert(RestAreaRequestDTO request){ 
        System.out.println("[RestAreaService] insert >>> "+request); 
        
        RestAreaEntity restArea = restRepository.save( 
            RestAreaEntity.builder() 
                .name(request.getName()) 
                .comment(request.getComment()) 
                .direction(request.getDirection()) 
                .build() 
        ); 
        return RestAreaResponseDTO.builder() 
                .restAreaId(restArea.getRestAreaId())
                .name(restArea.getName()) 
                .comment(restArea.getComment()) 
                .direction(restArea.getDirection()) 
                .build(); 
    }

    // LIST 전체 조회
    public List<RestAreaResponseDTO> list(){
        System.out.println("[RestAreaService] LIST >>> ");

        List<RestAreaEntity> list = restRepository.findAll() ;
        return list.stream()
                .map(entity -> RestAreaResponseDTO.builder()
                                .restAreaId(entity.getRestAreaId())
                                .name(entity.getName())
                                .comment(entity.getComment())
                                .direction(entity.getDirection()) 
                                .build())
                .toList() ; 
    }

 
    // FINDREST 휴게소 일부 조회
    public RestAreaResponseDTO findRest(Integer restAreaId){
        System.out.println("[RestAreaService] findRest >>> ");

        RestAreaEntity restAreaEntity =
            restRepository.findById(restAreaId)
                .orElseThrow(() -> 
                    new RuntimeException("해당 휴게소가 존재하지 않습니다"));
                  
        RestAreaResponseDTO response = 
            RestAreaResponseDTO.fromEntity(restAreaEntity) ;
        return response ;
        
        
    }



            
}