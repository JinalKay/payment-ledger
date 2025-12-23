package com.payment.ledger.controller;

import com.payment.ledger.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

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