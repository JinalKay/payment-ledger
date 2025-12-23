package com.payment.ledger.controller;

import com.payment.ledger.model.Wallet;
import com.payment.ledger.repository.WalletRepository;
import com.payment.ledger.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    // 1. Create Wallet (This was missing!)
    @GetMapping
    public String createWallet(@RequestParam String username) {
        // This explicitly sets the 100.00
        Wallet wallet = new Wallet(username, new BigDecimal("100.00"));
        walletRepository.save(wallet);
        return "Wallet created for " + username + " with ID: " + wallet.getId() + " | Balance: 100.00";
    }

    // 2. Transfer Money
    @GetMapping("/transfer")
    public String transfer(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam BigDecimal amount,
            @RequestParam String key) {
        try {
            walletService.transfer(from, to, amount, key);
            return "Transfer successful!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}