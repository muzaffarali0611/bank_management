package com.bankmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    
    @Column(name = "customer_id", unique = true, nullable = false)
    private String customerId;
    
    @Column(name = "credit_score")
    private Integer creditScore;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType = CustomerType.REGULAR;
    
    @Column(name = "annual_income", precision = 19, scale = 2)
    private BigDecimal annualIncome;
    
    @Column(name = "employment_status")
    private String employmentStatus;
    
    @Column(name = "employer_name")
    private String employerName;
    
    @Column(name = "kyc_verified", nullable = false)
    private Boolean kycVerified = false;
    
    @Column(name = "kyc_verified_at")
    private LocalDateTime kycVerifiedAt;
    
    @Column(name = "kyc_verified_by")
    private Long kycVerifiedBy;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
    
    // Enums
    public enum CustomerType { 
        REGULAR, PREMIUM, VIP, CORPORATE 
    }
    
    // Constructors
    public Customer() {}
    
    public Customer(String username, String passwordHash, String firstName, String lastName, String email) {
        super(username, passwordHash, firstName, lastName, email);
        this.customerId = generateCustomerId();
    }
    
    // Business methods
    private String generateCustomerId() {
        return "CUST" + System.currentTimeMillis() % 1000000;
    }
    
    public BigDecimal getTotalBalance() {
        return accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public boolean hasActiveLoan() {
        return loans.stream().anyMatch(loan -> loan.getStatus() == Loan.LoanStatus.ACTIVE);
    }
    
    public boolean isEligibleForLoan() {
        return creditScore != null && creditScore >= 650 && 
               kycVerified && 
               !hasActiveLoan();
    }
    
    // Getters and Setters
    public String getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(String customerId) { 
        this.customerId = customerId; 
    }
    
    public Integer getCreditScore() { 
        return creditScore; 
    }
    
    public void setCreditScore(Integer creditScore) { 
        this.creditScore = creditScore; 
    }
    
    public CustomerType getCustomerType() { 
        return customerType; 
    }
    
    public void setCustomerType(CustomerType customerType) { 
        this.customerType = customerType; 
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
    
    public List<Account> getAccounts() { 
        return accounts; 
    }
    
    public void setAccounts(List<Account> accounts) { 
        this.accounts = accounts; 
    }
    
    public List<Transaction> getTransactions() { 
        return transactions; 
    }
    
    public void setTransactions(List<Transaction> transactions) { 
        this.transactions = transactions; 
    }
    
    public List<Loan> getLoans() { 
        return loans; 
    }
    
    public void setLoans(List<Loan> loans) { 
        this.loans = loans; 
    }
    
    public List<Card> getCards() { 
        return cards; 
    }
    
    public void setCards(List<Card> cards) { 
        this.cards = cards; 
    }
}
