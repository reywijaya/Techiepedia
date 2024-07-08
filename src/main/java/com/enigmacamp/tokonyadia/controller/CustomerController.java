package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.entities.Customer;
import com.enigmacamp.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier("customer")
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add-customer")
    public CustomerResponse addCustomer(@RequestBody CustomerRequest customerRequest) {
        return convertToCustomerResponse(customerService.addCustomer(customerRequest));
    }

    @GetMapping("/customer")
    public CustomerResponse getCustomer(@RequestParam String id) {
        return convertToCustomerResponse(customerService.getCustomerById(id));
    }

    @GetMapping("/all-customers")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers().stream().map(this::convertToCustomerResponse).toList();
    }

    @PatchMapping("/update-customer")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return convertToCustomerResponse(customerService.updateCustomer(customerRequest));
    }

    @DeleteMapping("/delete-customer")
    public void deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
    }

    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress()).build();
    }
}