package com.kishansetu.kishan_setu_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String API_KEY = "API";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public String getWeatherByCity(String city) {
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public String getWeatherByCoords(double lat, double lon) {
        String url = BASE_URL + "?lat=" + lat
                + "&lon=" + lon
                + "&appid=" + API_KEY
                + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}