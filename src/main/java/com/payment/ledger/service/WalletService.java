package com.payment.ledger.service;

import com.payment.ledger.model.Transaction;
import com.payment.ledger.model.TransactionType;
import com.payment.ledger.model.Wallet;
import com.payment.ledger.repository.TransactionRepository;
import com.payment.ledger.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount, String idempotencyKey) {
        // 1. Check for duplicate request
        String debitKey = idempotencyKey + "_DEBIT";
        if (transactionRepository.existsByIdempotencyKey(debitKey)) {
            throw new RuntimeException("Duplicate transaction! Request already processed.");
        }

        // 2. Fetch wallets with Pessimistic Locking
        Wallet fromWallet = walletRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
        Wallet toWallet = walletRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        // 3. Validation
        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // 4. Update Balances
        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

       // 5. Save Audit Logs (Transactions)
        // Make sure 'amount' is passed in both lines below!
        transactionRepository.save(new Transaction(fromId, TransactionType.DEBIT, amount, debitKey));
        transactionRepository.save(new Transaction(toId, TransactionType.CREDIT, amount, idempotencyKey + "_CREDIT"));
    }
}