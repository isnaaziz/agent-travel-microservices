package com.agent.travel.service.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MidtransClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "midtrans", fallbackMethod = "midtransFallback")
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Map<String, Object> postTransaction(String snapUrl, HttpEntity<Map<String, Object>> requestEntity) {
        log.info("Sending request to Midtrans Snap API (with Circuit Breaker): {}", snapUrl);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(snapUrl, requestEntity, Map.class);
        return responseEntity.getBody();
    }

    public Map<String, Object> midtransFallback(String snapUrl, HttpEntity<Map<String, Object>> requestEntity, Throwable t) {
        log.error("Midtrans API call failed! Circuit Breaker Fallback executed. Reason: {}", t.getMessage());
        throw new IllegalStateException("Midtrans API is currently unavailable: " + t.getMessage(), t);
    }
}
