package com.payment.ledger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletId;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // Now it picks up the public file we just made

    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    private LocalDateTime timestamp;

    public Transaction(Long walletId, TransactionType type, BigDecimal amount) {
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }
}