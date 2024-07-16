package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestTemplateController {

    private final RestTemplateService restTemplateService;

    @PostMapping("/create-transaction")
    public Map<String, Object> createTransaction(@RequestBody Map<String, Object> transactionRequest) {
        return restTemplateService.createTransaction(transactionRequest);
    }
}