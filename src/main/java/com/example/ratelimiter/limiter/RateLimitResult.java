package com.example.ratelimiter.limiter;

public class RateLimitResult {

    private final boolean allowed;
    private final int remaining;
    private final long resetTimeMs;

    public RateLimitResult(boolean allowed, int remaining, long resetTimeMs) {
        this.allowed = allowed;
        this.remaining = remaining;
        this.resetTimeMs = resetTimeMs;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemaining() {
        return remaining;
    }

    public long getResetTimeSeconds() {
        return resetTimeMs / 1000;
    }
}
