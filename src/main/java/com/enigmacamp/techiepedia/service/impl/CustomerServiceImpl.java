package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.request.CustomerRequest;
import com.enigmacamp.techiepedia.model.dto.response.CustomerResponse;
import com.enigmacamp.techiepedia.model.entities.Customer;
import com.enigmacamp.techiepedia.repository.CustomerRepository;
import com.enigmacamp.techiepedia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public CustomerResponse addCustomer(CustomerRequest request) {
        Customer customer = convertCustomerRequestToCustomerEntity(request);
        customerRepository.save(customer);
        return convertToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest request) {
        findByIdOrThrow(request.getId());
        Customer customer = convertCustomerRequestToCustomerEntity(request);
        customerRepository.saveAndFlush(customer);
        return convertToCustomerResponse(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = findByIdOrThrow(id);
        customer.setDeleted(true);
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAllByDeletedFalse().stream().map(this::convertToCustomerResponse).toList();
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return convertToCustomerResponse(findByIdOrThrow(id));
    }

    @Override
    public void saveAllCustomers(List<CustomerRequest> requests) {
        requests.stream().map(this::convertCustomerRequestToCustomerEntity).forEach(customerRepository::saveAndFlush);
    }

    @Override
    public Page<CustomerResponse> getCustomerPerPage(Pageable pageable) {
        return convertToPageResponse(customerRepository.findAllByDeletedFalse(pageable));
    }

    Customer findByIdOrThrow(String id) {
        List<Customer> customers = customerRepository.findAllByDeletedFalse();
        return customers.stream().filter(customer -> customer.getId().equals(id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Page<CustomerResponse> convertToPageResponse(Page<Customer> customers) {
        return customers.map(this::convertToCustomerResponse);
    }

    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }

    private Customer convertCustomerRequestToCustomerEntity(CustomerRequest request) {
        return Customer.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .deleted(false)
                .build();
    }
}