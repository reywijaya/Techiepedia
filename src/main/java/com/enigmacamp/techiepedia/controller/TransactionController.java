package com.enigmacamp.techiepedia.controller;

import com.enigmacamp.techiepedia.constant.APIUrl;
import com.enigmacamp.techiepedia.model.dto.request.TransactionRequest;
import com.enigmacamp.techiepedia.model.dto.response.TransactionResponse;
import com.enigmacamp.techiepedia.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Qualifier("transaction")
@RequestMapping(APIUrl.TRANSACTION_API)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add-transaction")
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }
}