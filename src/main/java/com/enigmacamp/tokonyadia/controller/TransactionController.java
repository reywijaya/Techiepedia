package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.dto.response.TransactionResponse;
import com.enigmacamp.tokonyadia.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Qualifier("transaction")
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/add-transaction")
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }
}