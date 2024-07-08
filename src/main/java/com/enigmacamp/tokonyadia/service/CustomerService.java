package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(CustomerRequest request);
    void deleteCustomer(String id);
    CustomerResponse getCustomer(String id);
    List<CustomerResponse> getAllCustomers();
    Customer getCustomerById(String id);
}