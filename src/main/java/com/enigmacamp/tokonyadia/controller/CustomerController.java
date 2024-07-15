package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.AvatarResponse;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.PageResponse;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@Qualifier("customer")
@RequestMapping(APIUrl.CUSTOMER_API)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private final CustomerService customerService;
    private final FileStorageService fileStorageService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    @PostMapping("/add-customer")
    public CustomerResponse addCustomer(@RequestBody CustomerRequest request) {
        return customerService.addCustomer(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    @GetMapping("/customer")
    public CustomerResponse getCustomer(@RequestParam String id) {
        return customerService.getCustomerById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    @GetMapping("/all-customers")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    @PatchMapping("/update-customer")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest request) {
        return customerService.updateCustomer(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    @DeleteMapping("/delete-customer")
    public HttpStatus deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    @PostMapping("/save-all")
    public HttpStatus saveAllCustomers(@RequestBody List<CustomerRequest> requests) {
        customerService.saveAllCustomers(requests);
        return HttpStatus.CREATED;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
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

    @PostMapping("/upload-avatar")
    public ResponseEntity<CommonResponse<AvatarResponse>> uploadAvatar(@RequestParam(name = "avatar", defaultValue = "avatar") MultipartFile file, HttpServletRequest request) {
        String id = (String) request.getAttribute("id");
        String fileName = fileStorageService.storeFile(file, id);

        String fileDownloadUri = "http:localhost:8080" + request.getRequestURI() + "/" + fileName;

        AvatarResponse avatarResponse = AvatarResponse.builder()
                .url(fileDownloadUri)
                .name(fileName)
                .build();

        CommonResponse<AvatarResponse> commonResponse = CommonResponse.<AvatarResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully uploaded avatar")
                .data(avatarResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/upload-avatar/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFile(fileName);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        String contentType;
        try {
            Path filePath = resource.getFile().toPath();
            contentType = Files.probeContentType(filePath);
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}