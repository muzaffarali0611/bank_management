package com.bankmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_payments")
public class LoanPayment extends BaseEntity {
    
    @Column(name = "payment_number", unique = true, nullable = false)
    private String paymentNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    
    @Column(name = "payment_amount", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "0.01", message = "Payment amount must be at least 0.01")
    private BigDecimal paymentAmount;
    
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;
    
    @Column(name = "processed_date")
    private LocalDateTime processedDate;
    
    @Column(name = "reference_number")
    private String referenceNumber;
    
    @Column(name = "late_fee", precision = 19, scale = 2)
    private BigDecimal lateFee = BigDecimal.ZERO;
    
    @Column(name = "principal_portion", precision = 19, scale = 2)
    private BigDecimal principalPortion;
    
    @Column(name = "interest_portion", precision = 19, scale = 2)
    private BigDecimal interestPortion;
    
    @Column(name = "remaining_balance", precision = 19, scale = 2)
    private BigDecimal remainingBalance;
    
    // Enums
    public enum PaymentType { 
        REGULAR, EXTRA, LATE, DEFAULT, EARLY_PAYOFF 
    }
    
    public enum PaymentStatus { 
        PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED 
    }
    
    // Constructors
    public LoanPayment() {}
    
    public LoanPayment(Loan loan, BigDecimal paymentAmount, PaymentType paymentType) {
        this.loan = loan;
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
        this.paymentDate = LocalDateTime.now();
        this.paymentNumber = generatePaymentNumber();
    }
    
    // Business methods
    private String generatePaymentNumber() {
        return "PAY" + System.currentTimeMillis() % 1000000;
    }
    
    public void process() {
        this.status = PaymentStatus.PROCESSING;
        this.processedDate = LocalDateTime.now();
    }
    
    public void complete() {
        this.status = PaymentStatus.COMPLETED;
    }
    
    public void fail(String reason) {
        this.status = PaymentStatus.FAILED;
    }
    
    public BigDecimal getTotalAmount() {
        return paymentAmount.add(lateFee);
    }
    
    // Getters and Setters
    public String getPaymentNumber() { return paymentNumber; }
    public void setPaymentNumber(String paymentNumber) { this.paymentNumber = paymentNumber; }
    
    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }
    
    public BigDecimal getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(BigDecimal paymentAmount) { this.paymentAmount = paymentAmount; }
    
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    
    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }
    
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    
    public LocalDateTime getProcessedDate() { return processedDate; }
    public void setProcessedDate(LocalDateTime processedDate) { this.processedDate = processedDate; }
    
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    
    public BigDecimal getLateFee() { return lateFee; }
    public void setLateFee(BigDecimal lateFee) { this.lateFee = lateFee; }
    
    public BigDecimal getPrincipalPortion() { return principalPortion; }
    public void setPrincipalPortion(BigDecimal principalPortion) { this.principalPortion = principalPortion; }
    
    public BigDecimal getInterestPortion() { return interestPortion; }
    public void setInterestPortion(BigDecimal interestPortion) { this.interestPortion = interestPortion; }
    
    public BigDecimal getRemainingBalance() { return remainingBalance; }
    public void setRemainingBalance(BigDecimal remainingBalance) { this.remainingBalance = remainingBalance; }
}
