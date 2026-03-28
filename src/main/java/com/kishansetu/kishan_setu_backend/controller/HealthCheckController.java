package com.kishansetu.kishan_setu_backend.controller;


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
@RequestMapping("/health-check")
public class HealthCheckController {
    @GetMapping
    public String HealthCheck(){
        return "Kishan setu backend running fine";
    }
}
