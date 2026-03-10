package com.banking.service;

import com.banking.model.dto.AccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccounts();
    AccountDto createAccount(Long userId, String accountType);
    void depositMoney(String accountNumber, Double amount);
    void withdrawMoney(String accountNumber, Double amount);
}