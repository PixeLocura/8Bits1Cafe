
package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.service.AdminTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    public AdminTransactionController(AdminTransactionService transactionService) {
        this.adminTransactionService = transactionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUser(@PathVariable UUID userId) {
        List<TransactionDTO> transactions = adminTransactionService.getTransactionsByUser(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {

        TransactionDTO created = adminTransactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable UUID id) {

        TransactionDTO transactionDTO = adminTransactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping("/{transactionId}/details")
    public ResponseEntity<List<TransactionDetailDTO>> getTransactionDetails(@PathVariable UUID transactionId) {
        List<TransactionDetailDTO> details = adminTransactionService.getTransactionDetails(transactionId);
        if (details.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(details);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> list = adminTransactionService.getAllTransactions();
        return ResponseEntity.ok(list);
    }
}
