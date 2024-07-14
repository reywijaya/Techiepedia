package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.entities.Customer;
import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = Customer.builder()
                .id("1")
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .address("123 Street")
                .birthDate(new Date())
                .deleted(false)
                .build();

        request = CustomerRequest.builder()
                .id("1")
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .address("123 Street")
                .birthDate(new Date())
                .build();
    }

    @Test
    void addCustomer() {

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.addCustomer(request);

        assertNotNull(response);
        assertEquals(request.getId(), response.getId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer() {

        when(customerRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(customer));
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.updateCustomer(request);

        assertNotNull(response);
        assertEquals(request.getId(), response.getId());
        verify(customerRepository, times(1)).findAllByDeletedFalse();
        verify(customerRepository, times(1)).saveAndFlush(any(Customer.class));
    }

    @Test
    void deleteCustomer() {

        when(customerRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(customer));
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        customerService.deleteCustomer("1");

        assertTrue(customer.getDeleted());
        verify(customerRepository, times(1)).findAllByDeletedFalse();
        verify(customerRepository, times(1)).saveAndFlush(any(Customer.class));
    }

    @Test
    void getAllCustomers() {

        when(customerRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(customer));

        List<CustomerResponse> responses = customerService.getAllCustomers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(customerRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void getCustomerById() {

        when(customerRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(customer));

        CustomerResponse response = customerService.getCustomerById("1");

        assertNotNull(response);
        assertEquals(customer.getId(), response.getId());
        verify(customerRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void saveAllCustomers() {

        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        customerService.saveAllCustomers(Collections.singletonList(request));

        verify(customerRepository, times(1)).saveAndFlush(any(Customer.class));
    }

    @Test
    void getCustomerPerPage() {

        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer), pageable, 1);

        when(customerRepository.findAllByDeletedFalse(pageable)).thenReturn(customerPage);

        Page<CustomerResponse> responsePage = customerService.getCustomerPerPage(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        verify(customerRepository, times(1)).findAllByDeletedFalse(pageable);
    }

    @Test
    void findByIdOrThrow() {

        when(customerRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(customer));

        Customer result = customerService.findByIdOrThrow("1");

        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        verify(customerRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void findByIdOrThrow_NotFound() {
        when(customerRepository.findAllByDeletedFalse()).thenReturn(Collections.emptyList());

        assertThrows(ResponseStatusException.class, () -> customerService.findByIdOrThrow("1"));
        verify(customerRepository, times(1)).findAllByDeletedFalse();
    }
}