package com.mini.mini_2.rest_area.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;



@Service
public class RestAreaService {

    @Autowired
    private RestAreaRepository restRepository;

    // 휴게소 생성
    public RestAreaResponseDTO create(RestAreaRequestDTO request){ 
        System.out.println("[RestAreaService] create"); 
        
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

    // 전체 조회
    public List<RestAreaResponseDTO> findAll() {
        System.out.println("[RestAreaService] findAll");

        List<RestAreaEntity> list = restRepository.findAll();
        return list.stream()
                .map(entity -> RestAreaResponseDTO.fromEntity(entity))
                .toList();
    }

    // ID 기반 휴게소 단건 조회
    public RestAreaResponseDTO findByRestAreaId(Integer restAreaId) {
        System.out.println("[RestAreaService] findByRestAreaId");

        RestAreaEntity restAreaEntity =
            restRepository.findById(restAreaId)
                .orElseThrow(() -> 
                    new RuntimeException("해당 휴게소가 존재하지 않습니다"));
                  
        RestAreaResponseDTO response = 
            RestAreaResponseDTO.fromEntity(restAreaEntity) ;
        return response ;
          
    }

    // CODE 기반 휴게소 단건 조회
    public RestAreaResponseDTO findByCode(String code){
        System.out.println("[RestAreaService] findByCode");

        Optional<RestAreaEntity> restAreaEntity = restRepository.findByCode(code);
        
        if(restAreaEntity.isPresent()) {
            return RestAreaResponseDTO.fromEntity(restAreaEntity.get()) ;
            
        }
        else {
            return null;
        }
    }

    // 휴게소 수정
    public RestAreaResponseDTO update(Integer restAreaId, RestAreaRequestDTO request) {
        System.out.println("[RestAreaService] update");

        RestAreaEntity updated = RestAreaEntity.builder()
                .restAreaId(restAreaId)
                .name(request.getName())
                .direction(request.getDirection())
                .code(request.getCode())
                .tel(request.getTel())
                .address(request.getAddress())
                .routeName(request.getRouteName())
                .build();

        RestAreaEntity saved = restRepository.save(updated);

        return RestAreaResponseDTO.fromEntity(saved);

    }

    // 휴게소 삭제
    public boolean delete(Integer restAreaId) {

        RestAreaEntity rest = restRepository.findById(restAreaId)
                .orElseThrow(() -> new RuntimeException("휴게소가 존재하지 않습니다. ID: " + restAreaId));

        restRepository.delete(rest);

        return true;
    }

    // Direction 기반 휴게소 조회
    public List<RestAreaResponseDTO> findByDirection(String direction) {
        return restRepository.findAll().stream()
                .filter(rest -> rest.getDirection().equalsIgnoreCase(direction))
                .map(rest -> new RestAreaResponseDTO(
                        rest.getRestAreaId(),
                        rest.getName(),
                        rest.getDirection(),
                        rest.getCode(),
                        rest.getTel(),
                        rest.getAddress(),
                        rest.getRouteName(),
                        rest.getXValue(),
                        rest.getYValue()))
                .collect(Collectors.toList());
    }

            
}