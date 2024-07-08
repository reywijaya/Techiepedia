package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.entities.Customer;
import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Qualifier("customer")
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(CustomerRequest request) {

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(CustomerRequest request) {
        findByIdOrThrow(request.getId());
        Customer customer = convertCustomerRequestToCustomerEntity(request);
        customerRepository.saveAndFlush(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.delete(findByIdOrThrow(id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        return findByIdOrThrow(id);
    }

    private Customer findByIdOrThrow(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Customer convertCustomerRequestToCustomerEntity(CustomerRequest request) {
        return Customer.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .deleted(false)
                .build();
    }
}