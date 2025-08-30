package com.bankmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    
    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;
    
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
    
    @Column(name = "currency", nullable = false)
    private String currency = "USD";
    
    @Column(name = "description")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    @Column(name = "processed_date")
    private LocalDateTime processedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private Staff processedBy;
    
    @Column(name = "reference_number")
    private String referenceNumber;
    
    @Column(name = "fee_amount", precision = 19, scale = 2)
    private BigDecimal feeAmount = BigDecimal.ZERO;
    
    @Column(name = "exchange_rate", precision = 19, scale = 6)
    private BigDecimal exchangeRate = BigDecimal.ONE;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @Column(name = "location")
    private String location;
    
    // Enums
    public enum TransactionType { 
        DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT, REFUND, CHARGE, INTEREST, FEE 
    }
    
    public enum TransactionStatus { 
        PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED, REVERSED 
    }
    
    // Constructors
    public Transaction() {}
    
    public Transaction(Account fromAccount, Account toAccount, TransactionType transactionType, 
                      BigDecimal amount, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
        this.transactionId = generateTransactionId();
    }
    
    // Business methods
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() % 1000000;
    }
    
    public boolean isTransfer() {
        return transactionType == TransactionType.TRANSFER && fromAccount != null && toAccount != null;
    }
    
    public boolean isInternalTransfer() {
        return isTransfer() && fromAccount.getCustomer().getId().equals(toAccount.getCustomer().getId());
    }
    
    public BigDecimal getTotalAmount() {
        return amount.add(feeAmount);
    }
    
    public void process() {
        this.status = TransactionStatus.PROCESSING;
        this.processedDate = LocalDateTime.now();
    }
    
    public void complete() {
        this.status = TransactionStatus.COMPLETED;
    }
    
    public void fail(String reason) {
        this.status = TransactionStatus.FAILED;
        this.description = this.description + " - FAILED: " + reason;
    }
    
    // Getters and Setters
    public String getTransactionId() { 
        return transactionId; 
    }
    
    public void setTransactionId(String transactionId) { 
        this.transactionId = transactionId; 
    }
    
    public Account getFromAccount() { 
        return fromAccount; 
    }
    
    public void setFromAccount(Account fromAccount) { 
        this.fromAccount = fromAccount; 
    }
    
    public Account getToAccount() { 
        return toAccount; 
    }
    
    public void setToAccount(Account toAccount) { 
        this.toAccount = toAccount; 
    }
    
    public TransactionType getTransactionType() { 
        return transactionType; 
    }
    
    public void setTransactionType(TransactionType transactionType) { 
        this.transactionType = transactionType; 
    }
    
    public BigDecimal getAmount() { 
        return amount; 
    }
    
    public void setAmount(BigDecimal amount) { 
        this.amount = amount; 
    }
    
    public String getCurrency() { 
        return currency; 
    }
    
    public void setCurrency(String currency) { 
        this.currency = currency; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public TransactionStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(TransactionStatus status) { 
        this.status = status; 
    }
    
    public LocalDateTime getTransactionDate() { 
        return transactionDate; 
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) { 
        this.transactionDate = transactionDate; 
    }
    
    public LocalDateTime getProcessedDate() { 
        return processedDate; 
    }
    
    public void setProcessedDate(LocalDateTime processedDate) { 
        this.processedDate = processedDate; 
    }
    
    public Staff getProcessedBy() { 
        return processedBy; 
    }
    
    public void setProcessedBy(Staff processedBy) { 
        this.processedBy = processedBy; 
    }
    
    public String getReferenceNumber() { 
        return referenceNumber; 
    }
    
    public void setReferenceNumber(String referenceNumber) { 
        this.referenceNumber = referenceNumber; 
    }
    
    public BigDecimal getFeeAmount() { 
        return feeAmount; 
    }
    
    public void setFeeAmount(BigDecimal feeAmount) { 
        this.feeAmount = feeAmount; 
    }
    
    public BigDecimal getExchangeRate() { 
        return exchangeRate; 
    }
    
    public void setExchangeRate(BigDecimal exchangeRate) { 
        this.exchangeRate = exchangeRate; 
    }
    
    public String getIpAddress() { 
        return ipAddress; 
    }
    
    public void setIpAddress(String ipAddress) { 
        this.ipAddress = ipAddress; 
    }
    
    public String getUserAgent() { 
        return userAgent; 
    }
    
    public void setUserAgent(String userAgent) { 
        this.userAgent = userAgent; 
    }
    
    public String getLocation() { 
        return location; 
    }
    
    public void setLocation(String location) { 
        this.location = location; 
    }
}
