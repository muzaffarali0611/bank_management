package com.bankmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loans")
public class Loan extends BaseEntity {
    
    @Column(name = "loan_number", unique = true, nullable = false)
    private String loanNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;
    
    @Column(name = "principal_amount", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "1000.0", message = "Principal amount must be at least 1000")
    private BigDecimal principalAmount;
    
    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal interestRate;
    
    @Column(name = "loan_term_months", nullable = false)
    @Min(value = 1, message = "Loan term must be at least 1 month")
    @Max(value = 360, message = "Loan term cannot exceed 360 months")
    private Integer loanTermMonths;
    
    @Column(name = "monthly_payment", precision = 19, scale = 2)
    private BigDecimal monthlyPayment;
    
    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "outstanding_balance", precision = 19, scale = 2)
    private BigDecimal outstandingBalance;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status = LoanStatus.PENDING_APPROVAL;
    
    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate;
    
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    
    @Column(name = "disbursement_date")
    private LocalDateTime disbursementDate;
    
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Staff approvedBy;
    
    @Column(name = "collateral_value", precision = 19, scale = 2)
    private BigDecimal collateralValue;
    
    @Column(name = "collateral_description")
    private String collateralDescription;
    
    @Column(name = "purpose")
    private String purpose;
    
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanPayment> payments = new ArrayList<>();
    
    // Enums
    public enum LoanType { 
        PERSONAL, HOME, AUTO, BUSINESS, STUDENT, MORTGAGE, LINE_OF_CREDIT 
    }
    
    public enum LoanStatus { 
        PENDING_APPROVAL, APPROVED, DISBURSED, ACTIVE, DEFAULTED, PAID_OFF, REJECTED 
    }
    
    // Constructors
    public Loan() {}
    
    public Loan(Customer customer, LoanType loanType, BigDecimal principalAmount, 
                BigDecimal interestRate, Integer loanTermMonths) {
        this.customer = customer;
        this.loanType = loanType;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.loanTermMonths = loanTermMonths;
        this.applicationDate = LocalDateTime.now();
        this.loanNumber = generateLoanNumber();
        calculateLoanDetails();
    }
    
    // Business methods
    private String generateLoanNumber() {
        return "LOAN" + System.currentTimeMillis() % 1000000;
    }
    
    private void calculateLoanDetails() {
        // Simple loan calculation (monthly compound interest)
        double monthlyRate = interestRate.doubleValue() / 100 / 12;
        double monthlyPaymentDouble = principalAmount.doubleValue() * 
            (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths)) / 
            (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
        
        this.monthlyPayment = BigDecimal.valueOf(monthlyPaymentDouble).setScale(2, java.math.RoundingMode.HALF_UP);
        this.totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(loanTermMonths));
        this.outstandingBalance = totalAmount;
    }
    
    public void approve(Staff approvedBy) {
        this.status = LoanStatus.APPROVED;
        this.approvedBy = approvedBy;
        this.approvalDate = LocalDateTime.now();
    }
    
    public void disburse() {
        this.status = LoanStatus.DISBURSED;
        this.disbursementDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusMonths(loanTermMonths);
    }
    
    public void makePayment(BigDecimal paymentAmount) {
        if (outstandingBalance.compareTo(paymentAmount) >= 0) {
            outstandingBalance = outstandingBalance.subtract(paymentAmount);
            if (outstandingBalance.compareTo(BigDecimal.ZERO) <= 0) {
                this.status = LoanStatus.PAID_OFF;
            }
        }
    }
    
    public boolean isOverdue() {
        return status == LoanStatus.ACTIVE && 
               dueDate != null && 
               LocalDateTime.now().isAfter(dueDate);
    }
    
    // Getters and Setters
    public String getLoanNumber() { return loanNumber; }
    public void setLoanNumber(String loanNumber) { this.loanNumber = loanNumber; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public LoanType getLoanType() { return loanType; }
    public void setLoanType(LoanType loanType) { this.loanType = loanType; }
    
    public BigDecimal getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(BigDecimal principalAmount) { this.principalAmount = principalAmount; }
    
    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    
    public Integer getLoanTermMonths() { return loanTermMonths; }
    public void setLoanTermMonths(Integer loanTermMonths) { this.loanTermMonths = loanTermMonths; }
    
    public BigDecimal getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(BigDecimal monthlyPayment) { this.monthlyPayment = monthlyPayment; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public BigDecimal getOutstandingBalance() { return outstandingBalance; }
    public void setOutstandingBalance(BigDecimal outstandingBalance) { this.outstandingBalance = outstandingBalance; }
    
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
    
    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }
    
    public LocalDateTime getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDateTime approvalDate) { this.approvalDate = approvalDate; }
    
    public LocalDateTime getDisbursementDate() { return disbursementDate; }
    public void setDisbursementDate(LocalDateTime disbursementDate) { this.disbursementDate = disbursementDate; }
    
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
    public Staff getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Staff approvedBy) { this.approvedBy = approvedBy; }
    
    public BigDecimal getCollateralValue() { return collateralValue; }
    public void setCollateralValue(BigDecimal collateralValue) { this.collateralValue = collateralValue; }
    
    public String getCollateralDescription() { return collateralDescription; }
    public void setCollateralDescription(String collateralDescription) { this.collateralDescription = collateralDescription; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    
    public List<LoanPayment> getPayments() { return payments; }
    public void setPayments(List<LoanPayment> payments) { this.payments = payments; }
}
