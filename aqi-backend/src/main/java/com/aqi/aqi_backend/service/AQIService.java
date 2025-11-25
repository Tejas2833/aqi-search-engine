package com.aqi.aqi_backend.service;

import com.aqi.aqi_backend.cache.CachedAQI;
import com.aqi.aqi_backend.model.AQIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AQIService {

    @Value("${aqicn.api.token}")
    private String apiToken;

    @Value("${aqicn.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, CachedAQI> cache = new ConcurrentHashMap<>();

    public AQIResponse getAQIData(String city) {
        System.out.println("🔍 Fetching AQI data for city: " + city);

        if (cache.containsKey(city) && !cache.get(city).isExpired()) {
            System.out.println("✅ Returning cached data for: " + city);
            return cache.get(city).getData();
        }

        String url = apiUrl + city + "/?token=" + apiToken;
        System.out.println("🌐 Calling external API: " + url);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map body = response.getBody();
            System.out.println("📦 API raw response: " + body);

            if (body == null || !"ok".equals(body.get("status"))) {
                throw new RuntimeException("City not found or API error");
            }

            Map data = (Map) body.get("data");

            AQIResponse result = new AQIResponse();
            result.setCity(((Map) data.get("city")).get("name").toString());
            result.setAqi((int) data.get("aqi"));
            result.setDominantPollutant((String) data.get("dominentpol"));
            result.setTime(((Map) data.get("time")).get("s").toString());

            Map<String, Object> iaqi = (Map<String, Object>) data.get("iaqi");
            Map<String, Double> pollutants = new HashMap<>();
            iaqi.forEach((k, v) -> {
                Object value = ((Map<String, Object>) v).get("v");
                if (value instanceof Number) {
                    pollutants.put(k, ((Number) value).doubleValue());
                }
            });
            result.setPollutants(pollutants);

            cache.put(city, new CachedAQI(result));
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch AQI data: " + e.getMessage());
        }
    }
}
