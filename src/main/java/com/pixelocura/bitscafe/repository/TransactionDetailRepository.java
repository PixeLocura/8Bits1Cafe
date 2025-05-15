// TransactionDetailRepository.java
package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.model.entity.TransactionDetail.TransactionDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, TransactionDetailId> {
    List<TransactionDetail> findByTransactionId(UUID transactionId);
}
