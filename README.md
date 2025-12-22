# PaymentLedger (High-Concurrency Wallet Service)

A backend service designed to handle financial transactions with strict **ACID compliance** and **concurrency control**.

This project demonstrates how to solve critical data integrity issues (like the "Lost Update" problem) in distributed systems using **Pessimistic Locking** and **Double-Entry Ledger** principles.

## 🚀 Tech Stack
* **Language:** Java 17 (OpenJDK)
* **Framework:** Spring Boot 3
* **Database:** PostgreSQL (Relational Data Integrity)
* **ORM:** Hibernate / Spring Data JPA
* **Build Tool:** Maven

## 🔑 Key Features

### 1. Concurrency Control (Row-Level Locking)
To prevent race conditions where two simultaneous requests could modify the same wallet balance, I implemented **Pessimistic Locking** (`SELECT ... FOR UPDATE`).
* **Implementation:** `@Lock(LockModeType.PESSIMISTIC_WRITE)` in `WalletRepository`.
* **Result:** Guarantees data consistency even under high load.

### 2. Double-Entry Ledger System
Instead of simply updating a balance, every financial movement creates a permanent Audit Log.
* **Debit/Credit:** Every transfer creates two transaction records (one DEBIT, one CREDIT).
* **Auditability:** The sum of all transaction history can be reconciled against the current balance.

### 3. ACID Transactions
All transfers are atomic. If the "Credit" step fails, the "Debit" step rolls back automatically using Spring's `@Transactional` management.

## 🛠️ How to Run Locally

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/payment-ledger.git](https://github.com/YOUR_USERNAME/payment-ledger.git)
    ```
2.  **Configure Database:**
    Update `src/main/resources/application.properties` with your Postgres credentials.
3.  **Run the App:**
    ```bash
    ./mvnw spring-boot:run
    ```
4.  **Test Endpoints:**
    * Create Wallet: `GET /wallets?username=Alice`
    * Transfer Funds: `GET /wallets/transfer?from=1&to=2&amount=50`
