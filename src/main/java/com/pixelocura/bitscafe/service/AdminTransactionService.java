// TransactionService.java
package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.TransactionDTO;

import java.util.List;
import java.util.UUID;

public interface AdminTransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransactionById(UUID id);
    List<TransactionDTO> getAllTransactions();
}
