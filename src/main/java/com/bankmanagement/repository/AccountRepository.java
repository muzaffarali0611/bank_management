package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByCustomerId(Long customerId);
    
    List<Account> findByAccountType(Account.AccountType accountType);
    
    List<Account> findByAccountStatus(Account.AccountStatus accountStatus);
    
    @Query("SELECT a FROM Account a WHERE a.balance < a.minimumBalance")
    List<Account> findOverdraftAccounts();
    
    @Query("SELECT a FROM Account a WHERE a.balance >= :minBalance")
    List<Account> findByMinimumBalance(@Param("minBalance") BigDecimal minBalance);
    
    @Query("SELECT a FROM Account a WHERE a.customer.id = :customerId AND a.accountStatus = 'ACTIVE'")
    List<Account> findActiveAccountsByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.customer.id = :customerId AND a.accountStatus = 'ACTIVE'")
    BigDecimal getTotalBalanceByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT COUNT(a) FROM Account a WHERE a.openingDate >= :startDate")
    Long countAccountsOpenedAfter(@Param("startDate") LocalDateTime startDate);
    
    boolean existsByAccountNumber(String accountNumber);
}
