package com.bankmanagement.controller;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.dto.CustomerRegistrationDto;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<CustomerDto> registerCustomer(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        CustomerDto customer = customerService.createCustomer(registrationDto);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or #id == authentication.principal.id")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        CustomerDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<CustomerDto> getCustomerByUsername(@PathVariable String username) {
        CustomerDto customer = customerService.getCustomerByUsername(username);
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping("/customer-id/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<CustomerDto> getCustomerByCustomerId(@PathVariable String customerId) {
        CustomerDto customer = customerService.getCustomerByCustomerId(customerId);
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<CustomerDto>> getAllCustomers(Pageable pageable) {
        Page<CustomerDto> customers = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/type/{customerType}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<CustomerDto>> getCustomersByType(@PathVariable Customer.CustomerType customerType) {
        List<CustomerDto> customers = customerService.getCustomersByType(customerType);
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/active-verified")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<CustomerDto>> getActiveVerifiedCustomers() {
        List<CustomerDto> customers = customerService.getActiveVerifiedCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or #id == authentication.principal.id")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) {
        CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/verify-kyc")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<CustomerDto> verifyKyc(@PathVariable Long id, @RequestParam Long verifiedBy) {
        CustomerDto customer = customerService.verifyKyc(id, verifiedBy);
        return ResponseEntity.ok(customer);
    }
    
    @PutMapping("/{id}/credit-score")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<CustomerDto> updateCreditScore(@PathVariable Long id, @RequestParam Integer creditScore) {
        CustomerDto customer = customerService.updateCreditScore(id, creditScore);
        return ResponseEntity.ok(customer);
    }
}
