package com.aqi.aqi_backend.cache;

import com.aqi.aqi_backend.model.AQIResponse;

public class CachedAQI {
    private final AQIResponse data;
    private final long timestamp;

    public CachedAQI(AQIResponse data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - timestamp > 30 * 60 * 1000; // 30 minutes
    }

    public AQIResponse getData() {
        return data;
    }
}
