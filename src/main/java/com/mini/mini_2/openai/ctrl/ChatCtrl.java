package com.mini.mini_2.openai.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openai.domain.dto.ChatResponseDTO;
import com.mini.mini_2.openai.service.ChatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/mini/ai")
public class ChatCtrl {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(
            @RequestParam(name = "codes") List<String> codes) {
        System.out.println(">>>> chat ctrl path POST ");
        System.out.println(">>>> codes " + codes);

        ChatResponseDTO result = chatService.recommend(codes);

        return ResponseEntity.ok().body(result);
    }

}
