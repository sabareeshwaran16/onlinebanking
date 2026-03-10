package com.banking.service.impl;

import com.banking.model.dto.TransactionDto;
import com.banking.model.entity.AccountEntity;
import com.banking.model.entity.TransactionEntity;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDto> getTransactions() {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        List<TransactionEntity> transactionEntities = transactionRepository.findAll();

        for (int i = 0; i < transactionEntities.size(); i++) {
            TransactionDto dto = new TransactionDto();
            TransactionEntity transactionEntity = transactionEntities.get(i);

            dto.setId(transactionEntity.getId());
            dto.setFromAccountNumber(transactionEntity.getFromAccountEntity() != null ? 
                transactionEntity.getFromAccountEntity().getAccountNumber() : null);
            dto.setToAccountNumber(transactionEntity.getToAccountEntity() != null ? 
                transactionEntity.getToAccountEntity().getAccountNumber() : null);
            dto.setAmount(transactionEntity.getAmount());
            dto.setTransactionType(transactionEntity.getTransactionType());
            dto.setDescription(transactionEntity.getDescription());
            dto.setStatus(transactionEntity.getStatus());
            dto.setTransactionDate(transactionEntity.getTransactionDate());

            transactionDtos.add(dto);
        }
        return transactionDtos;
    }

    @Override
    public void transferFunds(String fromAccount, String toAccount, Double amount) {
        AccountEntity fromAccountEntity = accountRepository.findByAccountNumber(fromAccount)
                .orElseThrow(() -> new RuntimeException("From account not found"));
        
        AccountEntity toAccountEntity = accountRepository.findByAccountNumber(toAccount)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromAccountEntity.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balances
        fromAccountEntity.setBalance(fromAccountEntity.getBalance().subtract(BigDecimal.valueOf(amount)));
        toAccountEntity.setBalance(toAccountEntity.getBalance().add(BigDecimal.valueOf(amount)));

        accountRepository.save(fromAccountEntity);
        accountRepository.save(toAccountEntity);

        // Create transaction record
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setFromAccountEntity(fromAccountEntity);
        transactionEntity.setToAccountEntity(toAccountEntity);
        transactionEntity.setAmount(BigDecimal.valueOf(amount));
        transactionEntity.setTransactionType("TRANSFER");
        transactionEntity.setDescription("Fund Transfer");
        transactionEntity.setStatus("COMPLETED");

        transactionRepository.save(transactionEntity);
    }

    @Override
    public List<TransactionDto> getTransactionsByAccount(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<TransactionEntity> transactionEntities = transactionRepository.findByAccount(accountEntity);
        List<TransactionDto> transactionDtos = new ArrayList<>();

        for (int i = 0; i < transactionEntities.size(); i++) {
            TransactionDto dto = new TransactionDto();
            TransactionEntity entity = transactionEntities.get(i);

            dto.setId(entity.getId());
            dto.setFromAccountNumber(entity.getFromAccountEntity() != null ? 
                entity.getFromAccountEntity().getAccountNumber() : null);
            dto.setToAccountNumber(entity.getToAccountEntity() != null ? 
                entity.getToAccountEntity().getAccountNumber() : null);
            dto.setAmount(entity.getAmount());
            dto.setTransactionType(entity.getTransactionType());
            dto.setDescription(entity.getDescription());
            dto.setStatus(entity.getStatus());
            dto.setTransactionDate(entity.getTransactionDate());

            transactionDtos.add(dto);
        }
        return transactionDtos;
    }
}