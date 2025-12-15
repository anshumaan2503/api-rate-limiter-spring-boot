package com.example.ratelimiter.limiter;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Deque<Long>> requestMap = new ConcurrentHashMap<>();
    private final RateLimitConfig config;

    public RateLimiterService(RateLimitConfig config) {
        this.config = config;
    }

    public RateLimitResult checkRateLimit(String clientId) {
        long now = System.currentTimeMillis();

        requestMap.putIfAbsent(clientId, new ArrayDeque<>());
        Deque<Long> timestamps = requestMap.get(clientId);

        synchronized (timestamps) {
            while (!timestamps.isEmpty() &&
                   now - timestamps.peekFirst() > config.getWindowMs()) {
                timestamps.pollFirst();
            }

            int remaining = config.getMaxRequests() - timestamps.size();
            long resetTime =
                    config.getWindowMs() -
                    (timestamps.isEmpty() ? 0 : (now - timestamps.peekFirst()));

            if (remaining > 0) {
                timestamps.addLast(now);
                return new RateLimitResult(true, remaining - 1, resetTime);
            }

            return new RateLimitResult(false, 0, resetTime);
        }
    }
}
