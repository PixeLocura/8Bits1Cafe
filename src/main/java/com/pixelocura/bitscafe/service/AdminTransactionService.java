package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminTransactionService {
    List<Transaction> findAll();
    Page<Transaction> paginate(Pageable pageable);
    Transaction create(Transaction transaction);
    Transaction findById(UUID id);
    Transaction update(UUID id, Transaction updatedTransaction);
    void delete(UUID id);
}
