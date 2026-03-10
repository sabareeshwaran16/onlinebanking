package com.banking.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private AccountEntity fromAccountEntity;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private AccountEntity toAccountEntity;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status = "COMPLETED";

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate = LocalDateTime.now();
}