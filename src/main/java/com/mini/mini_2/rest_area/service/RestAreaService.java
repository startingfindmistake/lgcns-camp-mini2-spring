package com.mini.mini_2.rest_area.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

;


@Service
public class RestAreaService {

    @Autowired
    private RestAreaRepository restRepository ;
    
    // INSERT - 생성(등록)
    public RestAreaResponseDTO insert(RestAreaRequestDTO request){ 
        System.out.println("[RestAreaService] insert "); 
        
        String code = request.getCode();
        Optional<RestAreaEntity> existData = restRepository.findByCode(code);
        RestAreaEntity entityToSave;
        if (existData.isPresent()) {
            RestAreaEntity originData = existData.get();
            originData.setName(request.getName());
            originData.setDirection(request.getDirection());
            originData.setTel(request.getTel());
            originData.setAddress(request.getAddress());
            originData.setRouteName(request.getRouteName());
            originData.setXValue(request.getXValue());
            originData.setYValue(request.getYValue());
            entityToSave = originData;
        } else {
            entityToSave = request.toEntity();
        }

        RestAreaEntity restArea = restRepository.save(entityToSave);
        return RestAreaResponseDTO.fromEntity(restArea);
    }

    // LIST - 전체 조회
    public List<RestAreaResponseDTO> list(){
        System.out.println("[RestAreaService] list");

        List<RestAreaEntity> list = restRepository.findAll() ;
        return list.stream()
                .map(entity -> RestAreaResponseDTO.fromEntity(entity))
                .toList() ; 
    }

 
    // FINDREST - 휴게소 일부 조회
    public RestAreaResponseDTO findRest(Integer restAreaId){
        System.out.println("[RestAreaService] findRest ");

        RestAreaEntity restAreaEntity =
            restRepository.findById(restAreaId)
                .orElseThrow(() -> 
                    new RuntimeException("해당 휴게소가 존재하지 않습니다"));
                  
        RestAreaResponseDTO response = 
            RestAreaResponseDTO.fromEntity(restAreaEntity) ;
        return response ;
        
        
    }

    // delete 구성 후 추가


            
}