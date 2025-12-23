package com.payment.ledger.repository;

import com.payment.ledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // This naming convention tells Spring Data JPA to write the SQL query for us
    boolean existsByIdempotencyKey(String idempotencyKey);
}