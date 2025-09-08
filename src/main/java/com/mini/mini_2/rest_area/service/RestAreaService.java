package com.mini.mini_2.rest_area.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;


@Service
public class RestAreaService {

    @Autowired
    private RestAreaRepository restRepository ;
    
    // CREATE
    public boolean create(RestAreaRequestDTO request){
        System.out.println("[RestAreaService] create >>>"+request);
        RestAreaEntity e = RestAreaEntity.builder()
                .name(request.getName())
                .comment(request.getComment())
                .direction(request.getDirection())
                .build();
        restRepository.save(e);
        return e.getRest_area_id() != null;
    }

    // LIST
    public List<RestAreaResponseDTO> list(){
        System.out.println("[RestAreaService] LIST >>>");
        return restRepository.findAll().stream().map(RestAreaResponseDTO::fromEntity).toList() ;
    }

    // DETAIL
    public RestAreaResponseDTO get(Integer id){
        System.out.println("[RestAreaService] GET.ID >>> : " + id);
        return restRepository.findById(id).map(RestAreaResponseDTO::fromEntity).orElse(null);
    }
    


   
            
}