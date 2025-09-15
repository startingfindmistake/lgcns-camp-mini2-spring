package com.mini.mini_2.all;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

import lombok.Data;

@SpringBootTest
@Data
@Transactional
public class RestAreaTest {

    @Autowired
    private RestAreaRepository restAreaRepository;

    @Test
    public void test(){
        // CREATE
        RestAreaRequestDTO restAreaRequest = RestAreaRequestDTO.builder()
                .name("고양휴게소")
                .direction("상행")
                .code("0001")
                .tel("031-000-0000")
                .address("경기도 고양시 덕양구 수원문산고속도로 51")
                .routeName("문산고속도로")
                .build();

        RestAreaEntity restAreaEntity = restAreaRepository.save(restAreaRequest.toEntity());

        assertNotNull(restAreaEntity.getRestAreaId());
        assertEquals("고양휴게소", restAreaEntity.getName());


        // READ
        Optional<RestAreaEntity> foundById =
                restAreaRepository.findById(restAreaEntity.getRestAreaId());

        assertTrue(foundById.isPresent());
        assertEquals("고양휴게소", foundById.get().getName());

        // UPDATE
        RestAreaEntity entityToUpdate = foundById.orElseThrow();
        entityToUpdate.setTel("031-111-1111");
        RestAreaEntity updatedEntity = restAreaRepository.save(entityToUpdate);

        assertEquals("031-111-1111", updatedEntity.getTel());

        // DELETE
        restAreaRepository.deleteById(restAreaEntity.getRestAreaId());
        Optional<RestAreaEntity> deletedEntity =
                restAreaRepository.findById(restAreaEntity.getRestAreaId());

        assertFalse(deletedEntity.isPresent());
    }

    @Test
    public void filterTest() {
        List<RestAreaEntity> directionFilter = restAreaRepository.findAll();
        
        List<RestAreaEntity> up = directionFilter.stream()
                .filter(e -> "상행".equals(e.getDirection()))
                .toList();
        System.out.println("상행 휴게소: " + up);

        List<RestAreaEntity> down = directionFilter.stream()
                .filter(e -> "하행".equals(e.getDirection()))
                .toList();
        System.out.println("상행 휴게소: " + down);

    }
}
