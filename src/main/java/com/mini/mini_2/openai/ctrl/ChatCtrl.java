package com.mini.mini_2.openai.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openai.domain.dto.ChatRequestDTO;
import com.mini.mini_2.openai.domain.dto.ChatResponseDTO;
import com.mini.mini_2.openai.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/mini/ai")
@Tag(name = "AI Chat API", description = "AI 챗봇 추천 서비스 API")
public class ChatCtrl {

    @Autowired
    private ChatService chatService;

    @Operation(
        summary = "AI 음식 추천",
        description = "휴게소 코드 목록을 기반으로 AI가 음식을 추천합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200",
                         description = "AI Recommendation Success"),
            @ApiResponse(responseCode = "500",
                         description = "AI Recommendation Failed")
        }
    )
    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
        System.out.println(">>>> chat ctrl path POST ");
        System.out.println(">>>> request " + request);

        ChatResponseDTO result = chatService.recommend(request);

        return ResponseEntity.ok().body(result);
    }

}
