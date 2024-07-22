package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestTemplateServiceImpl implements RestTemplateService {

    private final RestTemplate restTemplate;
    private static final String MIDTRANS_API_URL = "https://app.sandbox.midtrans.com/snap/v1/transactions";

    @Override
    public Map<String, Object> createTransaction(Map<String, Object> transactionRequest) {
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(transactionRequest);

        ResponseEntity<Map> response = restTemplate.exchange(
                MIDTRANS_API_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        return response.getBody();
    }
}
