package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.TransactionRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.impl.AdminTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminTransactionServiceImplTest {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private AdminTransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        userRepository = mock(UserRepository.class);
        gameRepository = mock(GameRepository.class);

        // CONSTRUCTOR FINAL AJUSTADO: ya NO usa TransactionDetailRepository
        transactionService = new AdminTransactionServiceImpl(
                transactionRepository,
                gameRepository,
                userRepository
        );
    }

    @Test
    void createTransaction_shouldSaveTransactionCorrectly() {
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        TransactionDetailDTO detailDTO = new TransactionDetailDTO();
        detailDTO.setGameId(gameId);
        detailDTO.setPrice(29.99);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(userId);
        transactionDTO.setDetails(List.of(detailDTO));

        User user = new User();
        user.setId(userId);

        Game game = new Game();
        game.setId(gameId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        verify(transactionRepository).save(any(Transaction.class));
    }
}
