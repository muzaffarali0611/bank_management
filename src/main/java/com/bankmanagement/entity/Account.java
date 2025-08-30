package com.bankmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus = AccountStatus.PENDING_APPROVAL;
    
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "0.0", message = "Balance cannot be negative")
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(name = "currency", nullable = false)
    private String currency = "USD";
    
    @Column(name = "interest_rate", precision = 5, scale = 4)
    private BigDecimal interestRate;
    
    @Column(name = "minimum_balance", precision = 19, scale = 2)
    private BigDecimal minimumBalance;
    
    @Column(name = "daily_transaction_limit", precision = 19, scale = 2)
    private BigDecimal dailyTransactionLimit;
    
    @Column(name = "monthly_transaction_limit", precision = 19, scale = 2)
    private BigDecimal monthlyTransactionLimit;
    
    @Column(name = "opening_date")
    private LocalDateTime openingDate;
    
    @Column(name = "last_activity_date")
    private LocalDateTime lastActivityDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Staff approvedBy;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
    
    // Enums
    public enum AccountType { 
        SAVINGS, CHECKING, FIXED_DEPOSIT, CURRENT, BUSINESS, STUDENT, SENIOR_CITIZEN 
    }
    
    public enum AccountStatus { 
        PENDING_APPROVAL, ACTIVE, SUSPENDED, CLOSED, FROZEN, UNDER_REVIEW 
    }
    
    // Constructors
    public Account() {}
    
    public Account(Customer customer, AccountType accountType, String accountNumber) {
        this.customer = customer;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.openingDate = LocalDateTime.now();
        this.lastActivityDate = LocalDateTime.now();
    }
    
    // Business methods
    public boolean canWithdraw(BigDecimal amount) {
        if (accountStatus != AccountStatus.ACTIVE) {
            return false;
        }
        
        if (minimumBalance != null && balance.subtract(amount).compareTo(minimumBalance) < 0) {
            return false;
        }
        
        return true;
    }
    
    public void withdraw(BigDecimal amount) {
        if (!canWithdraw(amount)) {
            throw new IllegalStateException("Cannot withdraw amount: " + amount);
        }
        
        balance = balance.subtract(amount);
        lastActivityDate = LocalDateTime.now();
    }
    
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        balance = balance.add(amount);
        lastActivityDate = LocalDateTime.now();
    }
    
    public void addInterest() {
        if (interestRate != null && interestRate.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal interest = balance.multiply(interestRate).divide(new BigDecimal("100"));
            balance = balance.add(interest);
        }
    }
    
    public boolean isOverdraft() {
        return minimumBalance != null && balance.compareTo(minimumBalance) < 0;
    }
    
    // Getters and Setters
    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public void setAccountNumber(String accountNumber) { 
        this.accountNumber = accountNumber; 
    }
    
    public Customer getCustomer() { 
        return customer; 
    }
    
    public void setCustomer(Customer customer) { 
        this.customer = customer; 
    }
    
    public AccountType getAccountType() { 
        return accountType; 
    }
    
    public void setAccountType(AccountType accountType) { 
        this.accountType = accountType; 
    }
    
    public AccountStatus getAccountStatus() { 
        return accountStatus; 
    }
    
    public void setAccountStatus(AccountStatus accountStatus) { 
        this.accountStatus = accountStatus; 
    }
    
    public BigDecimal getBalance() { 
        return balance; 
    }
    
    public void setBalance(BigDecimal balance) { 
        this.balance = balance; 
    }
    
    public String getCurrency() { 
        return currency; 
    }
    
    public void setCurrency(String currency) { 
        this.currency = currency; 
    }
    
    public BigDecimal getInterestRate() { 
        return interestRate; 
    }
    
    public void setInterestRate(BigDecimal interestRate) { 
        this.interestRate = interestRate; 
    }
    
    public BigDecimal getMinimumBalance() { 
        return minimumBalance; 
    }
    
    public void setMinimumBalance(BigDecimal minimumBalance) { 
        this.minimumBalance = minimumBalance; 
    }
    
    public BigDecimal getDailyTransactionLimit() { 
        return dailyTransactionLimit; 
    }
    
    public void setDailyTransactionLimit(BigDecimal dailyTransactionLimit) { 
        this.dailyTransactionLimit = dailyTransactionLimit; 
    }
    
    public BigDecimal getMonthlyTransactionLimit() { 
        return monthlyTransactionLimit; 
    }
    
    public void setMonthlyTransactionLimit(BigDecimal monthlyTransactionLimit) { 
        this.monthlyTransactionLimit = monthlyTransactionLimit; 
    }
    
    public LocalDateTime getOpeningDate() { 
        return openingDate; 
    }
    
    public void setOpeningDate(LocalDateTime openingDate) { 
        this.openingDate = openingDate; 
    }
    
    public LocalDateTime getLastActivityDate() { 
        return lastActivityDate; 
    }
    
    public void setLastActivityDate(LocalDateTime lastActivityDate) { 
        this.lastActivityDate = lastActivityDate; 
    }
    
    public Staff getApprovedBy() { 
        return approvedBy; 
    }
    
    public void setApprovedBy(Staff approvedBy) { 
        this.approvedBy = approvedBy; 
    }
    
    public LocalDateTime getApprovedAt() { 
        return approvedAt; 
    }
    
    public void setApprovedAt(LocalDateTime approvedAt) { 
        this.approvedAt = approvedAt; 
    }
    
    public List<Transaction> getTransactions() { 
        return transactions; 
    }
    
    public void setTransactions(List<Transaction> transactions) { 
        this.transactions = transactions; 
    }
    
    public List<Card> getCards() { 
        return cards; 
    }
    
    public void setCards(List<Card> cards) { 
        this.cards = cards; 
    }
}
