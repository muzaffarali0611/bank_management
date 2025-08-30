package com.bankmanagement.dto;

import com.bankmanagement.entity.Address;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerDto {
    
    private Long id;
    private String customerId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private User.Gender gender;
    private Address address;
    private Customer.CustomerType customerType;
    private Integer creditScore;
    private BigDecimal annualIncome;
    private String employmentStatus;
    private String employerName;
    private Boolean kycVerified;
    private LocalDateTime kycVerifiedAt;
    private Long kycVerifiedBy;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public CustomerDto() {}
    
    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(String customerId) { 
        this.customerId = customerId; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
    
    public LocalDate getDateOfBirth() { 
        return dateOfBirth; 
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; 
    }
    
    public User.Gender getGender() { 
        return gender; 
    }
    
    public void setGender(User.Gender gender) { 
        this.gender = gender; 
    }
    
    public Address getAddress() { 
        return address; 
    }
    
    public void setAddress(Address address) { 
        this.address = address; 
    }
    
    public Customer.CustomerType getCustomerType() { 
        return customerType; 
    }
    
    public void setCustomerType(Customer.CustomerType customerType) { 
        this.customerType = customerType; 
    }
    
    public Integer getCreditScore() { 
        return creditScore; 
    }
    
    public void setCreditScore(Integer creditScore) { 
        this.creditScore = creditScore; 
    }
    
    public BigDecimal getAnnualIncome() { 
        return annualIncome; 
    }
    
    public void setAnnualIncome(BigDecimal annualIncome) { 
        this.annualIncome = annualIncome; 
    }
    
    public String getEmploymentStatus() { 
        return employmentStatus; 
    }
    
    public void setEmploymentStatus(String employmentStatus) { 
        this.employmentStatus = employmentStatus; 
    }
    
    public String getEmployerName() { 
        return employerName; 
    }
    
    public void setEmployerName(String employerName) { 
        this.employerName = employerName; 
    }
    
    public Boolean getKycVerified() { 
        return kycVerified; 
    }
    
    public void setKycVerified(Boolean kycVerified) { 
        this.kycVerified = kycVerified; 
    }
    
    public LocalDateTime getKycVerifiedAt() { 
        return kycVerifiedAt; 
    }
    
    public void setKycVerifiedAt(LocalDateTime kycVerifiedAt) { 
        this.kycVerifiedAt = kycVerifiedAt; 
    }
    
    public Long getKycVerifiedBy() { 
        return kycVerifiedBy; 
    }
    
    public void setKycVerifiedBy(Long kycVerifiedBy) { 
        this.kycVerifiedBy = kycVerifiedBy; 
    }
    
    public User.UserStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(User.UserStatus status) { 
        this.status = status; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
}
