package com.payment.ledger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data               // Generates Getters, Setters, toString, equals, hashCode
@NoArgsConstructor  // Generates the required No-Args constructor for Hibernate
@AllArgsConstructor // Generates a constructor for all fields (id, username, balance)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // We set the default value here so new wallets start with 100
    @Column(precision = 19, scale = 4)
    private BigDecimal balance = new BigDecimal("100.00");

    // Adding this specific constructor manually makes your Controller code easier to write
    public Wallet(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }
}