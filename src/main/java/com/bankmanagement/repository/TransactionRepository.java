package com.bankmanagement.repository;

import com.bankmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Optional<Transaction> findByTransactionId(String transactionId);
    
    List<Transaction> findByFromAccountId(Long fromAccountId);
    
    List<Transaction> findByToAccountId(Long toAccountId);
    
    List<Transaction> findByCustomerId(Long customerId);
    
    List<Transaction> findByTransactionType(Transaction.TransactionType transactionType);
    
    List<Transaction> findByStatus(Transaction.TransactionStatus status);
    
    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountIdOrderByDateDesc(@Param("accountId") Long accountId);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.fromAccount.id = :accountId AND t.transactionDate >= :startDate AND t.status = 'COMPLETED'")
    BigDecimal getTotalWithdrawalsByAccountAndDateRange(@Param("accountId") Long accountId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.toAccount.id = :accountId AND t.transactionDate >= :startDate AND t.status = 'COMPLETED'")
    BigDecimal getTotalDepositsByAccountAndDateRange(@Param("accountId") Long accountId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionDate >= :startDate AND t.status = 'COMPLETED'")
    Long countCompletedTransactionsAfter(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionDate >= :startDate AND t.status = 'COMPLETED'")
    BigDecimal getTotalTransactionVolumeAfter(@Param("startDate") LocalDateTime startDate);
}
