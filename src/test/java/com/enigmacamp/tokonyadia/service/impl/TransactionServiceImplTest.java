package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.TransactionDetailRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionDetailRepository transactionDetailRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private CustomerResponse customerResponse;
    private ProductResponse productResponse;
    private TransactionRequest transactionRequest;
    private TransactionDetailRequest transactionDetailRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerResponse = CustomerResponse.builder()
                .id("customer1")
                .name("John Doe")
                .phone("123456789")
                .email("johndoe@mail.com")
                .address("123 Street")
                .build();

        productResponse = ProductResponse.builder()
                .id("product1")
                .name("Product A")
                .price(100)
                .stock(10)
                .build();

        transactionDetailRequest = TransactionDetailRequest.builder()
                .productId("product1")
                .qty(2)
                .build();

        transactionRequest = TransactionRequest.builder()
                .customerId("customer1")
                .transactionDetailRequests(Collections.singletonList(transactionDetailRequest))
                .build();
    }

    @Test
    void createTransaction() {
        when(customerService.getCustomerById(anyString())).thenReturn(customerResponse);
        when(productService.getProductById(anyString())).thenReturn(productResponse);

        Product product = Product.builder()
                .id(productResponse.getId())
                .name(productResponse.getName())
                .price(productResponse.getPrice())
                .stock(productResponse.getStock())
                .build();

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

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .product(product)
                .qty(transactionDetailRequest.getQty())
                .productPrice(Long.valueOf(product.getPrice()))
                .transaction(transaction)
                .build();

        List<TransactionDetail> transactionDetails = Collections.singletonList(transactionDetail);
        transaction.setTransactionDetails(transactionDetails);

        when(transactionDetailRepository.save(any(TransactionDetail.class))).thenReturn(transactionDetail);
        when(transactionRepository.saveAndFlush(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse response = transactionService.createTransaction(transactionRequest);

        assertNotNull(response);
        assertEquals(customer.getId(), response.getCustomer().getId());
        assertEquals(transactionDetails.size(), response.getTransactions().size());
        assertEquals((long) product.getPrice() * transactionDetailRequest.getQty(), response.getTotalPayment());
        verify(customerService, times(1)).getCustomerById(anyString());
        verify(productService, times(1)).getProductById(anyString());
        verify(transactionDetailRepository, times(1)).save(any(TransactionDetail.class));
        verify(transactionRepository, times(1)).saveAndFlush(any(Transaction.class));
    }

    @Test
    void createTransaction_InsufficientStock() {
        productResponse.setStock(1); // setting stock less than requested qty
        when(customerService.getCustomerById(anyString())).thenReturn(customerResponse);
        when(productService.getProductById(anyString())).thenReturn(productResponse);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> transactionService.createTransaction(transactionRequest));

        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
        verify(customerService, times(1)).getCustomerById(anyString());
        verify(productService, times(1)).getProductById(anyString());
        verify(transactionDetailRepository, never()).save(any(TransactionDetail.class));
        verify(transactionRepository, never()).saveAndFlush(any(Transaction.class));
    }
}