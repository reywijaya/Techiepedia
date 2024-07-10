package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.PageResponse;
import com.enigmacamp.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier("customer")
@RequestMapping(APIUrl.CUSTOMER_API)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add-customer")
    public CustomerResponse addCustomer(@RequestBody CustomerRequest request) {
        return customerService.addCustomer(request);
    }

    @GetMapping("/customer")
    public CustomerResponse getCustomer(@RequestParam String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/all-customers")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PatchMapping("/update-customer")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest request) {
        return customerService.updateCustomer(request);
    }

    @DeleteMapping("/delete-customer")
    public HttpStatus deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
        return HttpStatus.OK;
    }

    @PostMapping("/save-all")
    public HttpStatus saveAllCustomers(@RequestBody List<CustomerRequest> requests) {
        customerService.saveAllCustomers(requests);
        return HttpStatus.CREATED;
    }

    @GetMapping("/page-customer")
    public PageResponse<CustomerResponse> getAllCustomersPerPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CustomerResponse> customerResponsePage = customerService.getCustomerPerPage(pageable);
        return new PageResponse<>(customerResponsePage);
    }
}