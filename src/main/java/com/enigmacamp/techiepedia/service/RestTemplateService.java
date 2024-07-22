package com.enigmacamp.techiepedia.service;

import java.util.Map;

public interface RestTemplateService {
    Map<String, Object> createTransaction(Map<String, Object> transactionRequest);
}