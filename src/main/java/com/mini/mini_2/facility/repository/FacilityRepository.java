package com.mini.mini_2.facility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Integer> {

    // restAreaId로 해당 휴게소 시설 목록 조회
    List<FacilityEntity> findByRestArea_RestAreaId(Integer restAreaId);
}
