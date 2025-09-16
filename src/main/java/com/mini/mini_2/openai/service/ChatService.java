package com.mini.mini_2.openai.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.mini_2.openai.domain.dto.ChatResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ChatService {

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api-key}")
    private String key;
    @Value("${openai.url}")
    private String url;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestAreaRepository restAreaRepository;

    public ChatService(RestAreaRepository restAreaRepository) {
        this.restAreaRepository = restAreaRepository;
    }

    public ChatResponseDTO recommend(List<String> codes) {
    System.out.println(">>> service recommend");

    List<RestAreaEntity> restAreas = restAreaRepository.findAll().stream()
            .filter(r -> codes.contains(r.getCode()))
            .toList();

    if (restAreas.isEmpty()) {
        throw new RuntimeException("해당 코드의 휴게소가 존재하지 않습니다: " + codes);
    }

    List<String> restAreaNames = restAreas.stream()
            .map(RestAreaEntity::getName)
            .toList();

    String prompt = """
        너는 휴게소 추천 전문가 AI야.
        다음 휴게소들 중에서 가장 추천할 만한 하나를 선택하고, 이유를 자세히 설명해줘.
        추천할 만한 하나의 휴게소의 대표 음식도 하나 말해줘.
        다른 휴게소는 언급하지 마.
        무조건 JSON 형식으로만 응답해야 해.

        조건:
        - 휴게소 목록: %s

        출력 예시:
        {
            "restArea": "<선택된 휴게소명>",
            "recommends": [
                {"name": "<선택된 휴게소명>", "food": "<추천 음식>", "reason": "<추천 이유>"}
            ]
        }
        """.formatted(restAreaNames);

        // messages 생성
        Map<String, Object> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", "넌 반드시 JSON 포맷을 키질 수 있는 AI Model 이야.");

        Map<String, Object> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", prompt);

        Map<String, Object> requestMsg = new HashMap<>();
        requestMsg.put("model", model);
        requestMsg.put("messages", List.of(systemMsg, userMsg));

        // Object -> json 문자열로 변환해서 요청
        String json = null;
        try {
            json = mapper.writeValueAsString(requestMsg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(">>>> request json ");
        System.out.println(json);

        // 요청
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + key)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        String responseJson = null;
        try {
            Response response = client.newCall(request).execute();
            System.out.println(">>>>> response");

            responseJson = response.body().string();
            System.out.println(responseJson);

            JsonNode node = mapper.readTree(responseJson);
            node.at("/choices/0/message/content").asText();
            return mapper.readValue(node.at("/choices/0/message/content").asText(), ChatResponseDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}