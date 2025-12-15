API RATE LIMITER – SPRING BOOT

OVERVIEW
This project implements a backend-level API Rate Limiter using Spring Boot.
The rate limiter protects REST APIs from excessive requests, abuse, and burst traffic by restricting how many requests a client can make within a fixed time window.
Rate limiting is enforced before controller execution using a filter-based approach.

PROBLEM STATEMENT
Public APIs without protection are vulnerable to:

High request bursts from clients or bots

Brute-force attacks on sensitive endpoints

Performance degradation and server overload

This project solves the problem by limiting requests per client and returning proper HTTP responses when limits are exceeded.

REQUEST FLOW

Client
↓
RateLimitFilter
↓
RateLimiterService
↓
Controller

RATE LIMITING DETAILS
Algorithm: Sliding Window
Scope: Per client IP
Default limit: 10 requests per 60 seconds

Old request timestamps are removed automatically, and new requests are allowed only if the client has not exceeded the configured limit.

CONFIGURATION
Rate limiting values are externalized using application.properties.

Example configuration:

rate-limit.max-requests=10
rate-limit.window-ms=60000

Limits can be changed without modifying the application code.

HTTP RATE LIMIT HEADERS
Each API response includes the following headers:

X-RateLimit-Limit – Maximum requests allowed
X-RateLimit-Remaining – Remaining requests in the current window
X-RateLimit-Reset – Time (in seconds) until the rate limit resets

When the limit is exceeded, the API returns:

HTTP 429 Too Many Requests
Too many requests. Try again later.

TECH STACK
Java 17
Spring Boot
Spring Web
Servlet Filters
ConcurrentHashMap

HOW TO RUN

Start the application using:
mvn spring-boot:run

Access the test endpoint:
http://localhost:8080/api/test

TESTING THE RATE LIMITER
Use the following PowerShell command to simulate burst traffic:

for ($i=1; $i -le 15; $i++) {
curl.exe http://localhost:8080/api/test

}

Expected behavior:

Initial requests return 200 OK

Requests beyond the limit return 429 Too Many Requests

KEY LEARNINGS

Implementing middleware using Spring Filters

Designing and applying rate limiting algorithms

Externalizing configuration for backend systems

Handling concurrent requests safely

Building production-oriented backend features

FUTURE IMPROVEMENTS

Redis-based distributed rate limiting

User-based limits using JWT authentication

Endpoint-specific rate limits

Automated unit and integration tests
