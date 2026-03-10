package com.banking.controller;

import com.banking.model.dto.TransactionDto;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getTransactions();
    }

    @PostMapping("/simple-transfer")
    public ResponseEntity<String> simpleTransfer(@RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam Double amount) {
        transactionService.transferFunds(fromAccount, toAccount, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transfer completed successfully");
    }

    @GetMapping("/{accountNumber}")
    public List<TransactionDto> getTransactionsByAccount(@PathVariable String accountNumber) {
        return transactionService.getTransactionsByAccount(accountNumber);
    }
}