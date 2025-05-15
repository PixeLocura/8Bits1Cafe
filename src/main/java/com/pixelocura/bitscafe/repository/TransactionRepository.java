// TransactionRepository.java
package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
