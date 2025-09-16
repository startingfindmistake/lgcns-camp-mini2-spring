package com.mini.mini_2.rest_area.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.service.RestAreaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/mini/restarea")
@Tag(name = "RestArea API", description = "휴게소 API")

public class RestAreaCtrl {

    @Autowired
    private RestAreaService restAreaService;

    @Operation(
        summary = "휴게소 생성",
        description = "휴게소를 생성해주세요."
    )

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody RestAreaRequestDTO request) {
        System.out.println("[RestAreaCtrl] create");

        RestAreaResponseDTO response = restAreaService.create(request);
        
        if (response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @Operation(
        summary = "휴게소 전체 조회",
        description = "휴게소 전체 목록입니다."
    )

    @GetMapping("/lists")
    public ResponseEntity<List<RestAreaResponseDTO>> findAll() {
        System.out.println("[RestAreaCtrl] findAll");

        List<RestAreaResponseDTO> response = restAreaService.findAll();

        return new ResponseEntity<List<RestAreaResponseDTO>>(response, HttpStatus.OK);
    }

    @Operation(
        summary = "ID 기반 휴게소 조회",
        description = "휴게소 ID를 입력해주세요."
    )

    @GetMapping("/lists/{restAreaId}")
    public ResponseEntity<RestAreaResponseDTO> findByRestAreaId(@PathVariable("restAreaId") Integer restAreaId) {
        System.out.println("[RestAreaCtrl] findByRestAreaId");
        
        RestAreaResponseDTO response = restAreaService.findByRestAreaId(restAreaId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    @Operation(
        summary = "휴게소 정보 수정",
        description = "휴게소 ID를 입력해주세요."
    )

    @PostMapping("/update/{restAreaId}")
    public ResponseEntity<RestAreaResponseDTO> update(
        @PathVariable("restAreaId") Integer restAreaId,
            @RequestBody RestAreaRequestDTO request) {

        try {
            RestAreaResponseDTO response = restAreaService.update(restAreaId, request);
            return ResponseEntity.ok(response); // 200
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 
        }
    }

    @Operation(
        summary = "휴게소 정보 삭제",
        description = "휴게소 ID를 입력해주세요."
    )

    @DeleteMapping("/delete/{restAreaId}")
    public ResponseEntity<Void> delete(@PathVariable("restAreaId") Integer restAreaId) {

        restAreaService.delete(restAreaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
        summary = "Direction 기반 휴게소 조회",
        description = "휴게소 Direction을 입력해주세요."
    )

    @GetMapping("/lists/{direction}")
    public ResponseEntity<List<RestAreaResponseDTO>> findByDirection(@PathVariable("direction") String direction) {
        List<RestAreaResponseDTO> response = restAreaService.findByDirection(direction);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);

    }
}
