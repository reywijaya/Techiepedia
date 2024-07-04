package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier(value = "customer")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

}