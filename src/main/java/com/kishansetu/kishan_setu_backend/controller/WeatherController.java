package com.kishansetu.kishan_setu_backend.controller;

import com.kishansetu.kishan_setu_backend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/by-coords")
    public ResponseEntity<?> getWeatherByCoords(
            @RequestParam double lat,
            @RequestParam double lon) {

        try {
            String data = weatherService.getWeatherByCoords(lat, lon);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 ADD THIS
            return ResponseEntity.status(500).body(e.getMessage()); // return real error
        }
    }
}