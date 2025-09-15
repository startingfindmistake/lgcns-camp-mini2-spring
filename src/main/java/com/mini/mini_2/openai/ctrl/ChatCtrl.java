package com.mini.mini_2.openai.ctrl;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openai.domain.dto.ChatResponseDTO;
import com.mini.mini_2.openai.service.ChatService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v2/inspire/ai")
public class ChatCtrl {

    @Autowired
    private ChatService chatService ;
        
    @PostMapping("/chat") 
    public ResponseEntity<ChatResponseDTO> chat(
                @RequestParam(name = "weather") String weather,
                @RequestParam(name = "location") String location) {
        System.out.println(">>>> chat ctrl path POST "); 
        System.out.println(">>>> prompt "+weather);
        System.out.println(">>>> prompt "+location);
        ChatResponseDTO result = chatService.recommendRestaurant(weather, location); 
        ////////////////////////
        return ResponseEntity.ok().body(result) ;  

    }

    
}
