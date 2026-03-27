package com.kishansetu.kishan_setu_backend.controller;

import com.kishansetu.kishan_setu_backend.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/ask/{q}")
    public String ask(@PathVariable String q) {
        return geminiService.askGemini(q);
    }
}