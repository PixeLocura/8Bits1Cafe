// TransactionService.java
package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;

import java.util.List;
import java.util.UUID;

public interface AdminTransactionService {
    List<TransactionDTO> getTransactionsByUser(UUID userId);

    List<TransactionDetailDTO> getTransactionDetails(UUID transactionId);

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(UUID id);

    List<TransactionDTO> getAllTransactions();

    TransactionDTO createTransactionForUserAndGames(UUID userId, List<UUID> gameIds);
}
