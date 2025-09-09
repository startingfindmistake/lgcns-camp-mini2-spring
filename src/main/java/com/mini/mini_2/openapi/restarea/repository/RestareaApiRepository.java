package com.mini.mini_2.openapi.restarea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mini.mini_2.openapi.restarea.dto.entity.RestareaEntity;

@Repository
public interface RestareaApiRepository extends JpaRepository<RestareaEntity, Integer>{
    
}
