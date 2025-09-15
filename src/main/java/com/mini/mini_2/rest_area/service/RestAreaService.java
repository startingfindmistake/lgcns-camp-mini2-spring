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

;

@Service
public class RestAreaService {

    @Autowired
    private RestAreaRepository restRepository;

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
    public List<RestAreaResponseDTO> list() {
        System.out.println("[RestAreaService] list");

        List<RestAreaEntity> list = restRepository.findAll();
        return list.stream()
                .map(entity -> RestAreaResponseDTO.fromEntity(entity))
                .toList();
    }

    // FINDREST - 휴게소 일부 조회
    public RestAreaResponseDTO findRest(Integer restAreaId) {
        System.out.println("[RestAreaService] findRest ");

        RestAreaEntity restAreaEntity =
            restRepository.findById(restAreaId)
                .orElseThrow(() -> 
                    new RuntimeException("해당 휴게소가 존재하지 않습니다"));
                  
        RestAreaResponseDTO response = 
            RestAreaResponseDTO.fromEntity(restAreaEntity) ;
        return response ;
        
        
    }
    public RestAreaResponseDTO findByCode(String code){
        System.out.println("[RestAreaService] findByCode ");

        Optional<RestAreaEntity> restAreaEntity = restRepository.findByCode(code);
        
        if(restAreaEntity.isPresent()) {
            return RestAreaResponseDTO.fromEntity(restAreaEntity.get()) ;
            
        }
        else {
            return null;
        }
    }

    // update
    public RestAreaResponseDTO update(Integer restAreaId, RestAreaRequestDTO request) {
        System.out.println("[RestAreaService] updateRest ");

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

    // delete
    public boolean delete(Integer restAreaId) {

        RestAreaEntity rest = restRepository.findById(restAreaId)
                .orElseThrow(() -> new RuntimeException("휴게소가 존재하지 않습니다. ID: " + restAreaId));

        restRepository.delete(rest);

        return true;
    }

    // 상하행
    public List<RestAreaResponseDTO> dircetion(String direction) {
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

    public RestAreaResponseDTO findByAddress(String addr) {
        
        Optional<RestAreaEntity> response = restRepository.findByAddress(addr);
        
        if(response.isPresent()) {
            return RestAreaResponseDTO.fromEntity(response.get());
        }
        return null;
    }

            
}