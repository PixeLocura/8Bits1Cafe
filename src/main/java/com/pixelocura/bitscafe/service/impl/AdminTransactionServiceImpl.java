package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.service.AdminTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminTransactionServiceImpl implements AdminTransactionService {
    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public Page<Transaction> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction findById(UUID id) {
        return null;
    }

    @Override
    public Transaction update(UUID id, Transaction updatedTransaction) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
