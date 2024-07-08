package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.model.dto.response.TransactionResponse;
import com.enigmacamp.tokonyadia.model.entities.Customer;
import com.enigmacamp.tokonyadia.model.entities.Product;
import com.enigmacamp.tokonyadia.model.entities.Transaction;
import com.enigmacamp.tokonyadia.model.entities.TransactionDetail;
import com.enigmacamp.tokonyadia.repository.TransactionDetailRepository;
import com.enigmacamp.tokonyadia.repository.TransactionRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ProductService;
import com.enigmacamp.tokonyadia.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Qualifier("transaction")
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        Customer customer = customerService.getCustomerById(transactionRequest.getCustomerId());

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .build();

        List<TransactionDetail> transactionDetails =
                transactionRequest.getTransactionDetailRequests().stream().map(transactionDetailRequest ->
                {
                    Product product = productService.getProductById(transactionDetailRequest.getProductId());
                    if (product.getStock() - transactionDetailRequest.getQty() < 0) {
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
                    }
                    TransactionDetail transactionDetail =
                            TransactionDetail.builder()
                                    .product(product)
                                    .qty(transactionDetailRequest.getQty())
                                    .productPrice(Long.valueOf(product.getPrice()))
                                    .transaction(transaction)
                                    .build();

                    return transactionDetailRepository.save(transactionDetail);
                }).toList();

        transaction.setTransactionDetails(transactionDetails);
        transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customer(transaction.getCustomer())
                .totalPayment(null)
                .date(transaction.getDate())
                .transactions(transaction.getTransactionDetails())
                .build();
    }
}