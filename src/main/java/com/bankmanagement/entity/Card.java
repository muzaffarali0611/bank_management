package com.bankmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {
    
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;
    
    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus = CardStatus.ACTIVE;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    
    @Column(name = "cvv_hash", nullable = false)
    private String cvvHash;
    
    @Column(name = "pin_hash")
    private String pinHash;
    
    @Column(name = "daily_limit", precision = 19, scale = 2)
    private BigDecimal dailyLimit;
    
    @Column(name = "monthly_limit", precision = 19, scale = 2)
    private BigDecimal monthlyLimit;
    
    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedDate;
    
    @Column(name = "activation_date")
    private LocalDateTime activationDate;
    
    @Column(name = "last_used_date")
    private LocalDateTime lastUsedDate;
    
    @Column(name = "is_contactless")
    private Boolean isContactless = false;
    
    @Column(name = "is_international")
    private Boolean isInternational = false;
    
    @Column(name = "replacement_reason")
    private String replacementReason;
    
    @Column(name = "previous_card_id")
    private Long previousCardId;
    
    // Enums
    public enum CardType { 
        DEBIT, CREDIT, PREPAID, BUSINESS_DEBIT, BUSINESS_CREDIT 
    }
    
    public enum CardStatus { 
        ACTIVE, INACTIVE, BLOCKED, EXPIRED, LOST, STOLEN, REPLACED 
    }
    
    // Constructors
    public Card() {}
    
    public Card(Account account, Customer customer, CardType cardType, String cardHolderName) {
        this.account = account;
        this.customer = customer;
        this.cardType = cardType;
        this.cardHolderName = cardHolderName;
        this.issuedDate = LocalDateTime.now();
        this.cardNumber = generateCardNumber();
        this.expiryDate = LocalDate.now().plusYears(3);
    }
    
    // Business methods
    private String generateCardNumber() {
        return "4" + String.format("%015d", System.currentTimeMillis() % 1000000000000000L);
    }
    
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
    
    public boolean isActive() {
        return cardStatus == CardStatus.ACTIVE && !isExpired();
    }
    
    public void activate() {
        this.cardStatus = CardStatus.ACTIVE;
        this.activationDate = LocalDateTime.now();
    }
    
    public void block(String reason) {
        this.cardStatus = CardStatus.BLOCKED;
        this.replacementReason = reason;
    }
    
    public void markAsLost() {
        this.cardStatus = CardStatus.LOST;
        this.replacementReason = "Card reported as lost";
    }
    
    public void markAsStolen() {
        this.cardStatus = CardStatus.STOLEN;
        this.replacementReason = "Card reported as stolen";
    }
    
    public void updateLastUsed() {
        this.lastUsedDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    
    public String getCardHolderName() { return cardHolderName; }
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
    
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public CardType getCardType() { return cardType; }
    public void setCardType(CardType cardType) { this.cardType = cardType; }
    
    public CardStatus getCardStatus() { return cardStatus; }
    public void setCardStatus(CardStatus cardStatus) { this.cardStatus = cardStatus; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public String getCvvHash() { return cvvHash; }
    public void setCvvHash(String cvvHash) { this.cvvHash = cvvHash; }
    
    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }
    
    public BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }
    
    public BigDecimal getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(BigDecimal monthlyLimit) { this.monthlyLimit = monthlyLimit; }
    
    public LocalDateTime getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDateTime issuedDate) { this.issuedDate = issuedDate; }
    
    public LocalDateTime getActivationDate() { return activationDate; }
    public void setActivationDate(LocalDateTime activationDate) { this.activationDate = activationDate; }
    
    public LocalDateTime getLastUsedDate() { return lastUsedDate; }
    public void setLastUsedDate(LocalDateTime lastUsedDate) { this.lastUsedDate = lastUsedDate; }
    
    public Boolean getIsContactless() { return isContactless; }
    public void setIsContactless(Boolean isContactless) { this.isContactless = isContactless; }
    
    public Boolean getIsInternational() { return isInternational; }
    public void setIsInternational(Boolean isInternational) { this.isInternational = isInternational; }
    
    public String getReplacementReason() { return replacementReason; }
    public void setReplacementReason(String replacementReason) { this.replacementReason = replacementReason; }
    
    public Long getPreviousCardId() { return previousCardId; }
    public void setPreviousCardId(Long previousCardId) { this.previousCardId = previousCardId; }
}
