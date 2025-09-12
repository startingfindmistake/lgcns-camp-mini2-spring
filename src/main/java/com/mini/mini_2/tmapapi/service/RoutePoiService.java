package com.mini.mini_2.tmapapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RoutePoiService {
    
    @Autowired
    private WebClient poiWebClient;
    
    
    
}
