package com.aqi.aqi_backend.controller;

import com.aqi.aqi_backend.model.AQIResponse;
import com.aqi.aqi_backend.service.AQIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AQIController {

    @Autowired
    private AQIService aqiService;

    @GetMapping("/aqi")
    public ResponseEntity<AQIResponse> getAQI(@RequestParam String city) {
        return ResponseEntity.ok(aqiService.getAQIData(city));
    }
}

