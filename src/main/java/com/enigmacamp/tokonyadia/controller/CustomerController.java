package com.enigmacamp.tokonyadia.controller;


import com.enigmacamp.tokonyadia.model.Customers;
import com.enigmacamp.tokonyadia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    List<Customers> customers;
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody Customers customer) {
        customerService.addCustomer(customer);
    }

    @GetMapping("find-customer/{id}")
    public void getCustomerById(@PathVariable Long id) {
        customerService.findCustomerById(id);
    }

    @GetMapping("/all-customer")
    public List<Customers> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @PatchMapping("/update-customer/{id}")
    public void updateCustomerById(@PathVariable Long id) {
        Customers updatedCustomer = customers.stream().filter(customer -> customer.getId().equals(id)).findFirst().orElse(null);
        customerService.updateCustomer(updatedCustomer);
    }

    @DeleteMapping("/delete-customer/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}