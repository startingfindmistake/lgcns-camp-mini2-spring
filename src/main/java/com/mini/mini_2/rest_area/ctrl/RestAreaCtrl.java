package com.mini.mini_2.rest_area.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.service.RestAreaService;

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

    // 생성(CREATE)
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody RestAreaRequestDTO request) {

        System.out.println("[RestAreaCtrl] POST /create >>> ");

        RestAreaResponseDTO response = restAreaService.insert(request);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    // 휴게소 전체 조회(RESTS)
    @GetMapping("/rests")
    public ResponseEntity<List<RestAreaResponseDTO>> rests() {
        System.out.println("[RestAreaCtrl] /rests ");

        List<RestAreaResponseDTO> list = restAreaService.list();

        return new ResponseEntity<List<RestAreaResponseDTO>>(list, HttpStatus.OK);
    }

    // 휴게소 일부 조회(READ)
    @GetMapping("/rests/{restAreaId}")
    public ResponseEntity<RestAreaResponseDTO> read(@PathVariable("restAreaId") Integer restAreaId) {
        System.out.println("[RestAreaCtrl] /read ");
        RestAreaResponseDTO response = restAreaService.findRest(restAreaId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // 휴게소 업데이트
    @PostMapping("update/{restAreaId}")
    public ResponseEntity<RestAreaResponseDTO> update(
            @PathVariable("restAreaId") Integer restAreaId,
            @RequestBody RestAreaRequestDTO request) {

        try {
            RestAreaResponseDTO response = restAreaService.update(restAreaId, request);
            return ResponseEntity.ok(response); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }

    // 휴게소 삭제
    @DeleteMapping("/delete/{restAreaId}")
    public ResponseEntity<Void> delete(
            @PathVariable("restAreaId") Integer restAreaId) {

        restAreaService.delete(restAreaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    // 상하행
    @GetMapping("/rests/direction/{direction}")
    public ResponseEntity<List<RestAreaResponseDTO>> direction(@PathVariable("direction") String direction) {
        List<RestAreaResponseDTO> list = restAreaService.dircetion(direction);

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        }
        return ResponseEntity.ok(list);

    }
}
