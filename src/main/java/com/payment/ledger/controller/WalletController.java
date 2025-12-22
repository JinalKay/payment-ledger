package com.payment.ledger.controller;

import com.payment.ledger.model.Wallet;
import com.payment.ledger.service.WalletService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal; // <--- This was missing!

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // 1. Create a wallet (GET for browser testing)
    @GetMapping
    public Wallet create(@RequestParam String username) {
        return walletService.createWallet(username);
    }

    // 2. Add Money safely (GET for browser testing)
    @GetMapping("/{id}/add")
    public Wallet addMoney(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return walletService.addMoney(id, amount);
    }
    @GetMapping("/transfer")
    public String transfer(@RequestParam Long from, @RequestParam Long to, @RequestParam BigDecimal amount) {
        walletService.transfer(from, to, amount);
        return "Transfer successful!";
    }
}