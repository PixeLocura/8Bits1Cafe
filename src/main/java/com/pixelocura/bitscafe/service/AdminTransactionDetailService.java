package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.model.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminTransactionDetailService {
    List<TransactionDetail> findAll();
    Page<TransactionDetail> paginate(Pageable pageable);
    TransactionDetail create(TransactionDetail transactionDetail);
    TransactionDetail findById(Transaction transaction, Game game);
    TransactionDetail update(Transaction transaction, Game game, TransactionDetail updatedTransactionDetail);
    void delete(Transaction transaction, Game game);
    List<TransactionDetail> findByTransaction(Transaction transaction);
    List<TransactionDetail> findByGame(Game game);
}
