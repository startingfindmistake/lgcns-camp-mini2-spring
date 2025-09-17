package com.mini.mini_2.openai.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.openai.domain.dto.ChatRequestDTO;
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

    public ChatResponseDTO recommend(ChatRequestDTO request) {
    System.out.println(">>> service recommend");

    List<RestAreaEntity> restAreas = restAreaRepository.findAll().stream()
            .filter(r -> request.getRestareaCodes().contains(r.getCode()))
            .toList();

    if (restAreas.isEmpty()) {
        throw new RuntimeException("해당 코드의 휴게소가 존재하지 않습니다: " + request.getRestareaCodes());
    }

    List<String> restAreaNames = restAreas.stream()
            .map(RestAreaEntity::getName)
            .toList();

    Map<String, List<String>> foodNames = restAreas.stream()
        .collect(Collectors.toMap(
                RestAreaEntity::getName,     
                f -> f.getFoods().stream()
                        .map(FoodEntity::getFoodName)
                        .toList()
        ));
    
    System.out.println(">>> service recommend request : " + request);
    String prompt = """
        너는 휴게소 추천 전문가 AI야.
        다음 휴게소들 중에서 가장 추천할 만한 하나를 선택하고, 이유를 자세히 설명해줘.
        추천할 만한 하나의 휴게소의 대표 음식도 하나 말해줘. 
        음식에 관한 정보는 내가 보내준 foodNames에 있는 정보를 토대로 대답을 해.
        이유에는 음식에 관한 설명과 휴게소의 관한 설명이 
        절반 정도의 비율을 가지고 설명해주면 좋겠어.
        다른 휴게소는 언급하지 마.
        사용자 요청이 자세한 경우 그걸 우선으로 처리하도록 해.
        무조건 JSON 형식으로만 응답해야 해.
        만약 foodNames의 값이 없는 휴게소가 있으면 추천 순위를 미루고 값이 없는 휴게소만 있으면
        출력 예시2의 형태로 출력을 해.
        그리고 백틱이 들어가면 json parsing 이 제대로 안되는거 같으니까 백틱은 절대로 사용하지마.

        조건:
        - 휴게소 목록: %s
        - 음식 목록: %s
        - 사용자 요청: %s

        출력 예시:
        {
            "restArea": "<선택된 휴게소명>",
            "recommends": [
                {"name": "<선택된 휴게소명>", "food": "<추천 음식>", "reason": "<추천 이유>"}
            ]
        }
            출력 예시2:
        {
            "restArea": "<선택된 휴게소명>",
            "recommends": [
                {"name": "<선택된 휴게소명>", "reason": "<추천 이유>"}
            ]
        }
        """.formatted(restAreaNames, foodNames, request.getUserRequest());

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
        // System.out.println(">>>> request json ");
        // System.out.println(json);

        // 요청
        Request aiRequest = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + key)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        String responseJson = null;
        try {
            Response response = client.newCall(aiRequest).execute();
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