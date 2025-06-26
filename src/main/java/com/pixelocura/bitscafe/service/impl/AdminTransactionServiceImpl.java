// TransactionServiceImpl.java
package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.mapper.TransactionMapper;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.TransactionDetailRepository;
import com.pixelocura.bitscafe.repository.TransactionRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.AdminTransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminTransactionServiceImpl implements AdminTransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private UserRepository userRepository; // final? x2
    private GameRepository gameRepository;

    public AdminTransactionServiceImpl(
            TransactionRepository transactionRepository,
            TransactionDetailRepository transactionDetailRepository,
            UserRepository userRepository,
            GameRepository gameRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionDetailRepository = transactionDetailRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public List<TransactionDTO> getTransactionsByUser(UUID userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return TransactionMapper.toTransactionDTOList(transactions);
    }

    @Override
    public List<TransactionDetailDTO> getTransactionDetails(UUID transactionId) {
        List<TransactionDetail> details = transactionDetailRepository.findByTransactionId(transactionId);
        return TransactionMapper.toTransactionDetailDTOList(details);
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();

        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);

        // Mapeamos detalles
        List<TransactionDetail> details = transactionDTO.getDetails().stream()
                .map(detailDTO -> {
                    TransactionDetail detail = new TransactionDetail();
                    detail.setTransaction(transaction); // asignamos la misma transacción
                    Game game = gameRepository.findById(detailDTO.getGameId())
                            .orElseThrow(() -> new RuntimeException("Game not found"));
                    detail.setGame(game);
                    detail.setPrice(detailDTO.getPrice());
                    return detail;
                }).collect(Collectors.toList());

        transaction.setDetails(details);

        // totalPrice y transactionDate se calculan en @PrePersist

        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(savedTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return TransactionMapper.toDTO(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO createTransactionForUserAndGames(UUID userId, List<UUID> gameIds) {
        // Find user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userId));

        // Fetch games by IDs
        List<Game> games = gameRepository.findAllById(gameIds);
        if (games.size() != gameIds.size()) {
            throw new IllegalArgumentException("Uno o más juegos no existen");
        }

        // Create transaction and details
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        // Details and total price will be set below

        List<TransactionDetail> details = games.stream().map(game -> {
            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(transaction);
            detail.setGame(game);
            detail.setPrice(game.getPrice());
            return detail;
        }).collect(Collectors.toList());
        transaction.setDetails(details);
        // totalPrice and transactionDate set by @PrePersist

        Transaction saved = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(saved);
    }
}
