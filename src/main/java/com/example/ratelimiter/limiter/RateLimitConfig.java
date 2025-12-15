package com.example.ratelimiter.limiter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitConfig {

    private int maxRequests;
    private long windowMs;

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public long getWindowMs() {
        return windowMs;
    }

    public void setWindowMs(long windowMs) {
        this.windowMs = windowMs;
    }
}
