package com.mini.mini_2.facility.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Integer> {

    // restAreaId로 휴게소별 편의 시설 조회
    List<FacilityEntity> findByRestArea_RestAreaId(Integer restAreaId);

    // 주어진 시설들을 갖춘 휴게소 엔티티 반환(ex 약국이 있는 휴게소만 출력) 
    @Query("""
           SELECT f.restArea 
           FROM   FacilityEntity f  
           WHERE  f.name IN :types
           GROUP BY f.restArea
           HAVING COUNT(DISTINCT f.name) = :#{#types.size()}
           """)             
    List<RestAreaEntity> findRestAreaByTypes(@Param("types") List<String> types); 


}
