package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.dto.request.TransactionRequest;
import com.enigmacamp.techiepedia.model.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}
