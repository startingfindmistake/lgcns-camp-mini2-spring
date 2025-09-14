package com.mini.mini_2.rest_area.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

@Repository
public interface RestAreaRepository extends JpaRepository<RestAreaEntity, Integer>{
    List<RestAreaEntity> findByDirection(String direction);
    

}


