package com.kishansetu.kishan_setu_backend.service;

import com.kishansetu.kishan_setu_backend.DTO.ChatReqDTO;
import com.kishansetu.kishan_setu_backend.model.ChatModel;
import com.kishansetu.kishan_setu_backend.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Autowired
    ChatRepo chatRepo;

    private final RestTemplate restTemplate;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String askGemini(ChatReqDTO chatReqDTO) {
        if (chatReqDTO.getId() == null || chatReqDTO.getId().isBlank() || chatReqDTO.getChat() == null || chatReqDTO.getChat().isBlank()){
            return "ID and Prompt required";
        }

        String url = apiUrl + "?key=" + apiKey;

        Map<String, Object> textPart = Map.of("text", chatReqDTO.getChat());

        Map<String, Object> content = Map.of(
                "parts", List.of(textPart)
        );

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(content)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        try {
            Map body = response.getBody();
            List candidates = (List) body.get("candidates");

            Map first = (Map) candidates.get(0);
            Map contentMap = (Map) first.get("content");
            List parts = (List) contentMap.get("parts");
            Map text = (Map) parts.get(0);
            String chatRes = text.get("text").toString();
            ChatModel chatModel = new ChatModel();
            chatModel.setChat(chatRes);
            chatModel.setUserId(chatReqDTO.getId());
            chatModel.setCreatedAt(LocalDateTime.now());
            chatModel.setChatQ(chatReqDTO.getChat());
            chatRepo.save(chatModel);
            return chatRes;

        } catch (Exception e) {
            return "Error parsing Gemini response";
        }
    }
}