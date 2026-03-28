package com.kishansetu.kishan_setu_backend.controller;

import com.kishansetu.kishan_setu_backend.DTO.CropRecommandReqDTO;
import com.kishansetu.kishan_setu_backend.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "https://kishan-setu-delta.vercel.app"
        },
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
@RestController
@RequestMapping("/api/crop-recommand")
public class CropRecommendController {
    @Autowired
    GeminiService geminiService;

    @PostMapping
    public String recommand(@RequestBody CropRecommandReqDTO cropRecommandReqDTO){
        return geminiService.askGeminiCropRecommend(cropRecommandReqDTO);
    }
}
