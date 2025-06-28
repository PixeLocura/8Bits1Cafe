package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.mapper.TransactionMapper;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.model.entity.TransactionDetail;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.TransactionRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.AdminTransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminTransactionServiceImpl implements AdminTransactionService {

    private final TransactionRepository transactionRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public AdminTransactionServiceImpl(
            TransactionRepository transactionRepository,
            GameRepository gameRepository,
            UserRepository userRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TransactionDTO> getTransactionsByUser(UUID userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailDTO> getTransactionDetails(UUID transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"))
                .getDetails()
                .stream()
                .map(TransactionMapper::toDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        // Validar y obtener usuario
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Crear entidad transacción
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionDate(ZonedDateTime.now());

        // Mapear detalles y vincular juegos
        List<TransactionDetail> details = transactionDTO.getDetails().stream().map(detailDTO -> {
            Game game = gameRepository.findById(detailDTO.getGameId())
                    .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + detailDTO.getGameId()));

            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(transaction);
            detail.setGame(game);
            detail.setPrice(detailDTO.getPrice());
            return detail;
        }).collect(Collectors.toList());

        transaction.setDetails(details);

        // Calcular total
        double total = details.stream().mapToDouble(TransactionDetail::getPrice).sum();
        transaction.setTotalPrice(total);

        // Guardar en cascada
        Transaction saved = transactionRepository.save(transaction);

        return TransactionMapper.toDTO(saved);
    }

    @Override
    public TransactionDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<Game> games = gameRepository.findAllById(gameIds);
        if (games.size() != gameIds.size()) {
            throw new IllegalArgumentException("Uno o más juegos no existen");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionDate(ZonedDateTime.now());

        List<TransactionDetail> details = games.stream().map(game -> {
            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(transaction);
            detail.setGame(game);
            detail.setPrice(game.getPrice());
            return detail;
        }).collect(Collectors.toList());

        transaction.setDetails(details);

        double total = details.stream().mapToDouble(TransactionDetail::getPrice).sum();
        transaction.setTotalPrice(total);

        Transaction saved = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(saved);
    }
}
