# High-Performance Payment Ledger System 💸

A robust, thread-safe financial ledger system built with **Java 17** and **Spring Boot**. This project demonstrates senior-level backend engineering principles required for high-scale payment systems.

## 🚀 Key Engineering Features

* **Pessimistic Locking (Concurrency Control):** Uses Database-level locks (`SELECT FOR UPDATE`) to prevent race conditions during simultaneous transfers.
* **Idempotency Guarantee:** Implemented an Idempotency Key layer to ensure that duplicate API requests (e.g., from a user double-clicking) do not result in double-charges.
* **Double-Entry Bookkeeping:** Every transfer creates two immutable transaction records (Debit & Credit), ensuring a perfect audit trail for financial compliance.
* **Fault Tolerance:** Managed transactions using Spring's `@Transactional` to ensure atomicity—if one part fails, the whole transfer rolls back.

## 🛠 Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Database:** PostgreSQL
* **ORM:** Spring Data JPA / Hibernate

## 📋 API Endpoints

### 1. Create Wallet
`GET /wallets?username=Alice`
Initializes a new wallet with a default balance of ₹100.

### 2. Idempotent Transfer
`GET /wallets/transfer?from=1&to=2&amount=30&key=UNIQUE_ID`
Performs a secure transfer. Use a unique `key` for every request.

---
*Developed for high-concurrency environments.*