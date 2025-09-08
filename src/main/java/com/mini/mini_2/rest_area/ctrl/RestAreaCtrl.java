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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v2/mini/restarea")
@Tag(name = "RestArea API", description = "휴게소 API")

public class RestAreaCtrl {

    @Autowired
    private RestAreaService restAreaService ; 

    // 생성(CREATE)
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody  RestAreaRequestDTO request) {
        System.out.println("[RestAreaCtrl] POST create >>> "+request);
        boolean ok = restAreaService.create(request) ;
        
        return ok ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
                  : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
           

    }

    
    // 전체 조회(LIST)
    @GetMapping("/list")
    public ResponseEntity<List<RestAreaResponseDTO>> list() {
        System.out.println("[RestAreaCtrl] GET list >>> ");
        return ResponseEntity.ok(restAreaService.list()) ;
    }

    // 일부 조회(DETAIL)
    @GetMapping("/{rest_area_id}")
    public ResponseEntity<RestAreaResponseDTO> detail(@PathVariable("rest_area_id") Integer id) {
        System.out.println("[RestAreaCtrl] GET {id} >>> "+id);
        RestAreaResponseDTO response = restAreaService.get(id) ;
        return (response == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                                  : ResponseEntity.ok(response);
    }
    
    
}

