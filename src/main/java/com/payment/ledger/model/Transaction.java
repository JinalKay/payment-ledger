package com.payment.ledger.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletId;
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(Long walletId, TransactionType type, BigDecimal amount, String idempotencyKey) {
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getWalletId() { return walletId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public LocalDateTime getTimestamp() { return timestamp; }
}