package com.bankmanagement.repository;

import com.bankmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByUsername(String username);
    
    Optional<Customer> findByEmail(String email);
    
    Optional<Customer> findByCustomerId(String customerId);
    
    List<Customer> findByCustomerType(Customer.CustomerType customerType);
    
    List<Customer> findByKycVerified(Boolean kycVerified);
    
    @Query("SELECT c FROM Customer c WHERE c.creditScore >= :minCreditScore")
    List<Customer> findByMinimumCreditScore(@Param("minCreditScore") Integer minCreditScore);
    
    @Query("SELECT c FROM Customer c WHERE c.annualIncome >= :minIncome")
    List<Customer> findByMinimumIncome(@Param("minIncome") BigDecimal minIncome);
    
    @Query("SELECT c FROM Customer c WHERE c.status = 'ACTIVE' AND c.kycVerified = true")
    List<Customer> findActiveVerifiedCustomers();
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.createdAt >= :startDate")
    Long countCustomersCreatedAfter(@Param("startDate") LocalDateTime startDate);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByCustomerId(String customerId);
}
