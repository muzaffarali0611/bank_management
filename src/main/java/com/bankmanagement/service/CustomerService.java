package com.bankmanagement.service;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.dto.CustomerRegistrationDto;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.User;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.exception.CustomerNotFoundException;
import com.bankmanagement.exception.DuplicateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@Transactional
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public CustomerService(CustomerRepository customerRepository, 
                         PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Cacheable(value = "customers", key = "#id")
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        
        return mapToDto(customer);
    }
    
    @Cacheable(value = "customers", key = "#username")
    public CustomerDto getCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: " + username));
        
        return mapToDto(customer);
    }
    
    public CustomerDto getCustomerByCustomerId(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with customer ID: " + customerId));
        
        return mapToDto(customer);
    }
    
    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(this::mapToDto);
    }
    
    public List<CustomerDto> getCustomersByType(Customer.CustomerType customerType) {
        List<Customer> customers = customerRepository.findByCustomerType(customerType);
        return customers.stream().map(this::mapToDto).toList();
    }
    
    public List<CustomerDto> getActiveVerifiedCustomers() {
        List<Customer> customers = customerRepository.findActiveVerifiedCustomers();
        return customers.stream().map(this::mapToDto).toList();
    }
    
    @Transactional
    @CacheEvict(value = "customers", allEntries = true)
    public CustomerDto createCustomer(@Valid CustomerRegistrationDto registrationDto) {
        // Validate unique constraints
        if (customerRepository.existsByUsername(registrationDto.getUsername())) {
            throw new DuplicateCustomerException("Username already exists: " + registrationDto.getUsername());
        }
        
        if (customerRepository.existsByEmail(registrationDto.getEmail())) {
            throw new DuplicateCustomerException("Email already exists: " + registrationDto.getEmail());
        }
        
        // Create new customer
        Customer customer = new Customer();
        customer.setUsername(registrationDto.getUsername());
        customer.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        customer.setFirstName(registrationDto.getFirstName());
        customer.setLastName(registrationDto.getLastName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setDateOfBirth(registrationDto.getDateOfBirth());
        customer.setGender(registrationDto.getGender());
        customer.setAddress(registrationDto.getAddress());
        customer.setStatus(User.UserStatus.PENDING_VERIFICATION);
        
        Customer savedCustomer = customerRepository.save(customer);
        
        return mapToDto(savedCustomer);
    }
    
    @Transactional
    @CacheEvict(value = "customers", key = "#id")
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        
        // Update fields
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setGender(customerDto.getGender());
        customer.setAddress(customerDto.getAddress());
        customer.setAnnualIncome(customerDto.getAnnualIncome());
        customer.setEmploymentStatus(customerDto.getEmploymentStatus());
        customer.setEmployerName(customerDto.getEmployerName());
        
        Customer updatedCustomer = customerRepository.save(customer);
        
        return mapToDto(updatedCustomer);
    }
    
    @Transactional
    @CacheEvict(value = "customers", key = "#id")
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        
        customer.setStatus(User.UserStatus.INACTIVE);
        customerRepository.save(customer);
    }
    
    @Transactional
    @CacheEvict(value = "customers", key = "#id")
    public CustomerDto verifyKyc(Long id, Long verifiedBy) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        
        customer.setKycVerified(true);
        customer.setKycVerifiedAt(LocalDateTime.now());
        customer.setKycVerifiedBy(verifiedBy);
        customer.setStatus(User.UserStatus.ACTIVE);
        
        Customer updatedCustomer = customerRepository.save(customer);
        
        return mapToDto(updatedCustomer);
    }
    
    @Transactional
    @CacheEvict(value = "customers", key = "#id")
    public CustomerDto updateCreditScore(Long id, Integer creditScore) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        
        customer.setCreditScore(creditScore);
        Customer updatedCustomer = customerRepository.save(customer);
        
        return mapToDto(updatedCustomer);
    }
    
    // Helper methods
    private CustomerDto mapToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setCustomerId(customer.getCustomerId());
        dto.setUsername(customer.getUsername());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setGender(customer.getGender());
        dto.setAddress(customer.getAddress());
        dto.setCustomerType(customer.getCustomerType());
        dto.setCreditScore(customer.getCreditScore());
        dto.setAnnualIncome(customer.getAnnualIncome());
        dto.setEmploymentStatus(customer.getEmploymentStatus());
        dto.setEmployerName(customer.getEmployerName());
        dto.setKycVerified(customer.getKycVerified());
        dto.setStatus(customer.getStatus());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        
        return dto;
    }
}
