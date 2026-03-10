package com.banking.repository;

import com.banking.model.entity.AccountEntity;
import com.banking.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.fromAccountEntity = ?1 OR t.toAccountEntity = ?1 ORDER BY t.transactionDate DESC")
    List<TransactionEntity> findByAccount(AccountEntity accountEntity);
}