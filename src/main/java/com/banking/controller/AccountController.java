package com.banking.controller;

import com.banking.model.dto.AccountDto;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAccounts();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam Long userId, @RequestParam String accountType) {
        AccountDto createdAccount = accountService.createAccount(userId, accountType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Account created successfully: " + createdAccount.getAccountNumber());
    }

    @PostMapping("/deposit")
    public ResponseEntity<Object> depositMoney(@RequestParam String accountNumber, @RequestParam Double amount) {
        accountService.depositMoney(accountNumber, amount);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdrawMoney(@RequestParam String accountNumber, @RequestParam Double amount) {
        accountService.withdrawMoney(accountNumber, amount);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}