package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.dto.request.CustomerRequest;
import com.enigmacamp.techiepedia.model.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(CustomerRequest request);
    void deleteCustomer(String id);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerById(String id);
    void saveAllCustomers(List<CustomerRequest> requests);
    Page<CustomerResponse> getCustomerPerPage(Pageable pageable);
}