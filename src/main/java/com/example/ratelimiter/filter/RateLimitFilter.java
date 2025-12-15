package com.example.ratelimiter.filter;

import com.example.ratelimiter.limiter.RateLimitConfig;
import com.example.ratelimiter.limiter.RateLimitResult;
import com.example.ratelimiter.limiter.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;
    private final RateLimitConfig config;

    public RateLimitFilter(RateLimiterService rateLimiterService,
                           RateLimitConfig config) {
        this.rateLimiterService = rateLimiterService;
        this.config = config;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        RateLimitResult result =
                rateLimiterService.checkRateLimit(clientIp);

        response.setHeader("X-RateLimit-Limit",
                String.valueOf(config.getMaxRequests()));
        response.setHeader("X-RateLimit-Remaining",
                String.valueOf(result.getRemaining()));
        response.setHeader("X-RateLimit-Reset",
                String.valueOf(result.getResetTimeSeconds()));

        if (!result.isAllowed()) {
            response.setStatus(429);
            response.getWriter()
                    .write("Too many requests. Try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
