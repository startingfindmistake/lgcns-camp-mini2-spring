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

    
    List<FacilityEntity> findByRestArea_RestAreaId(Integer restAreaId);

    @Query("""
           SELECT f.restArea 
           FROM   FacilityEntity f  
           WHERE  f.name IN :names
           GROUP BY f.restArea
           HAVING COUNT(DISTINCT f.name) = :#{#names.size()}
           """)             
    List<RestAreaEntity> findRestAreaByTypes(@Param("names") List<String> names); 


}
