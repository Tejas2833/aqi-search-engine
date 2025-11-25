package com.aqi.aqi_backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class AQIResponse {
    private String city;
    private int aqi;
    private String dominantPollutant;
    private Map<String, Double> pollutants;
    private String time;
}
