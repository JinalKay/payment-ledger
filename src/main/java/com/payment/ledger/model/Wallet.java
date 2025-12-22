package com.payment.ledger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Data   // <--- This generates setUsername, setBalance, etc.
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(precision = 19, scale = 4)
    private BigDecimal balance;
}