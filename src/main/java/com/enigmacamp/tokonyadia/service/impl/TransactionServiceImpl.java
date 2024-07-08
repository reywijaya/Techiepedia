package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.dto.response.TransactionResponse;
import com.enigmacamp.tokonyadia.model.Customer;
import com.enigmacamp.tokonyadia.model.Transaction;
import com.enigmacamp.tokonyadia.repository.TransactionRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ProductService;
import com.enigmacamp.tokonyadia.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("transaction")
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Customer customer = customerService.getCustomerById(transactionRequest.getCustomerId());
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .build();
        return null;
    }
}
