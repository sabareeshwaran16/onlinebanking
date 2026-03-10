package com.banking.service;

import com.banking.model.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getTransactions();
    void transferFunds(String fromAccount, String toAccount, Double amount);
    List<TransactionDto> getTransactionsByAccount(String accountNumber);
}