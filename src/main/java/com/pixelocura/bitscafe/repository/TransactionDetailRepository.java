// TransactionDetailRepository.java
package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.model.entity.TransactionDetail.TransactionDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, TransactionDetailId> {
}
