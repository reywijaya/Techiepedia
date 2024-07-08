package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.entities.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(CustomerRequest request);
    Customer updateCustomer(CustomerRequest request);
    void deleteCustomer(String id);
    List<Customer> getAllCustomers();
    Customer getCustomerById(String id);
}