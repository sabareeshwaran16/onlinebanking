package com.banking.service.impl;

import com.banking.model.dto.AccountDto;
import com.banking.model.entity.AccountEntity;
import com.banking.model.entity.TransactionEntity;
import com.banking.model.entity.UserEntity;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import com.banking.repository.UserRepository;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<AccountDto> getAccounts() {
        List<AccountDto> accountDtos = new ArrayList<>();
        List<AccountEntity> accountEntities = accountRepository.findAll();

        for (int i = 0; i < accountEntities.size(); i++) {
            AccountDto dto = new AccountDto();
            AccountEntity accountEntity = accountEntities.get(i);

            dto.setId(accountEntity.getId());
            dto.setAccountNumber(accountEntity.getAccountNumber());
            dto.setAccountType(accountEntity.getAccountType());
            dto.setBalance(accountEntity.getBalance());
            dto.setCreatedAt(accountEntity.getCreatedAt());

            accountDtos.add(dto);
        }
        return accountDtos;
    }

    @Override
    public AccountDto createAccount(Long userId, String accountType) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(generateAccountNumber());
        accountEntity.setAccountType(accountType);
        accountEntity.setUserEntity(userEntity);

        AccountEntity savedAccount = accountRepository.save(accountEntity);

        AccountDto responseDto = new AccountDto();
        responseDto.setId(savedAccount.getId());
        responseDto.setAccountNumber(savedAccount.getAccountNumber());
        responseDto.setAccountType(savedAccount.getAccountType());
        responseDto.setBalance(savedAccount.getBalance());

        return responseDto;
    }

    @Override
    public void depositMoney(String accountNumber, Double amount) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountEntity.setBalance(accountEntity.getBalance().add(BigDecimal.valueOf(amount)));
        accountRepository.save(accountEntity);

        // Record Transaction
        TransactionEntity transaction = new TransactionEntity();
        transaction.setToAccountEntity(accountEntity);
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setTransactionType("DEPOSIT");
        transaction.setDescription("Cash Deposit");
        transaction.setStatus("COMPLETED");
        transactionRepository.save(transaction);
    }

    @Override
    public void withdrawMoney(String accountNumber, Double amount) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (accountEntity.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        accountEntity.setBalance(accountEntity.getBalance().subtract(BigDecimal.valueOf(amount)));
        accountRepository.save(accountEntity);

        // Record Transaction
        TransactionEntity transaction = new TransactionEntity();
        transaction.setFromAccountEntity(accountEntity);
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setDescription("Cash Withdrawal");
        transaction.setStatus("COMPLETED");
        transactionRepository.save(transaction);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = "ACC" + String.format("%010d", random.nextInt(1000000000));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}