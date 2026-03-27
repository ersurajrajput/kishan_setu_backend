package com.kishansetu.kishan_setu_backend.controller;

import com.kishansetu.kishan_setu_backend.DTO.ChatReqDTO;
import com.kishansetu.kishan_setu_backend.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public String ask(@RequestBody ChatReqDTO chatReqDTO) {
        return geminiService.askGemini(chatReqDTO);
    }
}