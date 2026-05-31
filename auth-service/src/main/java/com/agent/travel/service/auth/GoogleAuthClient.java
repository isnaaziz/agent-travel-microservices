package com.agent.travel.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Map;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "googleAuth", fallbackMethod = "googleAuthFallback")
    @SuppressWarnings("unchecked")
    public Map<String, Object> verifyToken(String idToken) {
        String googleTokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
        log.info("Calling Google Tokeninfo API via RestTemplate...");
        return restTemplate.getForObject(googleTokenInfoUrl, Map.class);
    }

    public Map<String, Object> googleAuthFallback(String idToken, Throwable t) {
        log.error("Google Auth API failed! Executing fallback. Reason: {}", t.getMessage());
        Map<String, Object> mockPayload = new HashMap<>();
        mockPayload.put("email", "fallback-traveler@gmail.com");
        mockPayload.put("name", "Fallback Traveler (Google Offline)");
        return mockPayload;
    }
}
