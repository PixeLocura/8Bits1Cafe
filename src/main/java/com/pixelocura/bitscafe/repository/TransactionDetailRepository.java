package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, TransactionDetail.TransactionDetailId> {
}
