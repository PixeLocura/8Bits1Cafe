package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.service.AdminTransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminTransactionDetailServiceImpl implements AdminTransactionDetailService {
    @Override
    public List<TransactionDetail> findAll() {
        return List.of();
    }

    @Override
    public Page<TransactionDetail> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public TransactionDetail create(TransactionDetail transactionDetail) {
        return null;
    }

    @Override
    public TransactionDetail findById(Transaction transaction, Game game) {
        return null;
    }

    @Override
    public TransactionDetail update(Transaction transaction, Game game, TransactionDetail updatedTransactionDetail) {
        return null;
    }

    @Override
    public void delete(Transaction transaction, Game game) {

    }

    @Override
    public List<TransactionDetail> findByTransaction(Transaction transaction) {
        return List.of();
    }

    @Override
    public List<TransactionDetail> findByGame(Game game) {
        return List.of();
    }
}
