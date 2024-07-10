package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Qualifier("transaction")
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        CustomerResponse customerResponse = customerService.getCustomerById(transactionRequest.getCustomerId());

        Customer customer = Customer.builder()
                .id(customerResponse.getId())
                .name(customerResponse.getName())
                .phone(customerResponse.getPhone())
                .email(customerResponse.getEmail())
                .address(customerResponse.getAddress())
                .build();

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .date(new Date())
                .build();

        AtomicReference<Long> totalAmount = new AtomicReference<>(0L);

        List<TransactionDetail> transactionDetails =
                transactionRequest.getTransactionDetailRequests().stream().map(transactionDetailRequest ->
                {
                    ProductResponse response = productService.getProductById(transactionDetailRequest.getProductId());

                    Product product = Product.builder()
                            .id(response.getId())
                            .name(response.getName())
                            .price(response.getPrice())
                            .stock(response.getStock())
                            .build();

                    if (product.getStock() - transactionDetailRequest.getQty() < 0) {
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
                    }

                    product.setStock(product.getStock() - transactionDetailRequest.getQty());

                    TransactionDetail transactionDetail =
                            TransactionDetail.builder()
                                    .product(product)
                                    .qty(transactionDetailRequest.getQty())
                                    .productPrice(Long.valueOf(product.getPrice()))
                                    .transaction(transaction)
                                    .build();

                    totalAmount.updateAndGet(value -> value + ((long) product.getPrice() * transactionDetailRequest.getQty()));

                    return transactionDetailRepository.save(transactionDetail);
                }).toList();

        transaction.setTransactionDetails(transactionDetails);
        Transaction resultTransaction = transactionRepository.saveAndFlush(transaction);

        return TransactionResponse.builder()
                .id(resultTransaction.getId())
                .customer(resultTransaction.getCustomer())
                .totalPayment(totalAmount.get())
                .date(resultTransaction.getDate())
                .transactions(resultTransaction.getTransactionDetails())
                .build();
    }
}