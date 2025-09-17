package com.mini.mini_2.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.mini.mini_2.openapi.ctrl.OpenApiCtrl;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(true)
public class OpenApiTest {
    
    @Autowired
    private OpenApiCtrl openApiCtrl;
    
    
    
}
