package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.Customers;
import com.enigmacamp.tokonyadia.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public void addCustomer(Customers customer){
        customerRepo.save(customer);
    }

    public void updateCustomer(Customers customer){
        customerRepo.save(customer);
    }

    public void deleteCustomer(Long id){
        customerRepo.deleteById(id);
    }

    public void findCustomerById(Long id){
        customerRepo.findById(id);
    }

    public List<Customers> findAllCustomers(){
        return customerRepo.findAll();
    }
}