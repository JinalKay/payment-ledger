package com.payment.ledger.service;

import com.payment.ledger.model.Transaction;
import com.payment.ledger.model.TransactionType;
import com.payment.ledger.model.Wallet;
import com.payment.ledger.repository.TransactionRepository;
import com.payment.ledger.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository; // <--- New Dependency

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public Wallet createWallet(String username) {
        Wallet newWallet = new Wallet();
        newWallet.setUsername(username);
        newWallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(newWallet);
    }

    @Transactional
    public Wallet addMoney(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found!"));

        // 1. Update Balance
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        // 2. Record Transaction (Double Entry)
        Transaction creditTx = new Transaction(walletId, TransactionType.CREDIT, amount);
        transactionRepository.save(creditTx);

        return wallet;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Wallet toWallet = walletRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds!");
        }

        // 1. Move Money
        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));
        
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        // 2. Create Ledger Records (The "Paper Trail")
        Transaction debitTx = new Transaction(fromId, TransactionType.DEBIT, amount);
        Transaction creditTx = new Transaction(toId, TransactionType.CREDIT, amount);
        
        transactionRepository.save(debitTx);
        transactionRepository.save(creditTx);
    }
}