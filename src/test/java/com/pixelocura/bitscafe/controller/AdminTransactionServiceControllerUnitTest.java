package com.pixelocura.bitscafe.controller;
import com.pixelocura.bitscafe.exception.BadRequestException;
import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.service.AdminTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminTransactionServiceControllerUnitTest {

    @Mock
    private AdminTransactionService adminTransactionService;

    @InjectMocks
    private AdminTransactionController adminTransactionController;

    private UUID transactionId;
    private UUID userId;
    private TransactionDTO sampleTransactionDTO;
    private TransactionDetailDTO sampleTransactionDetailDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transactionId = UUID.randomUUID();
        userId = UUID.randomUUID();

        sampleTransactionDetailDTO = new TransactionDetailDTO();
        sampleTransactionDetailDTO.setTransactionId(transactionId);
        sampleTransactionDetailDTO.setGameId(UUID.randomUUID());
        sampleTransactionDetailDTO.setPrice(49.99);

        sampleTransactionDTO = new TransactionDTO();
        sampleTransactionDTO.setId(transactionId);
        sampleTransactionDTO.setUserId(userId);
        sampleTransactionDTO.setTotalPrice(49.99);
        sampleTransactionDTO.setTransactionDate(ZonedDateTime.now());
        sampleTransactionDTO.setDetails(Arrays.asList(sampleTransactionDetailDTO));
    }

    @Test
    @DisplayName("CP01 - Obtener transacciones por usuario: con datos existentes")
    void getTransactionsByUser_withExistingData_returnsOkAndList() {
        when(adminTransactionService.getTransactionsByUser(userId)).thenReturn(List.of(sampleTransactionDTO));

        ResponseEntity<List<TransactionDTO>> response = adminTransactionController.getTransactionsByUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(transactionId, response.getBody().get(0).getId());
        verify(adminTransactionService, times(1)).getTransactionsByUser(userId);
    }

    @Test
    @DisplayName("CP02 - Obtener transacciones por usuario: sin datos")
    void getTransactionsByUser_noData_returnsNoContent() {
        when(adminTransactionService.getTransactionsByUser(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<TransactionDTO>> response = adminTransactionController.getTransactionsByUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(adminTransactionService, times(1)).getTransactionsByUser(userId);
    }

    @Test
    @DisplayName("CP03 - Crear transacción: con datos válidos")
    void createTransaction_validData_returnsOkAndCreatedTransaction() {
        TransactionDTO transactionToCreate = new TransactionDTO();
        transactionToCreate.setUserId(userId);
        transactionToCreate.setTotalPrice(49.99);
        transactionToCreate.setTransactionDate(ZonedDateTime.now());
        transactionToCreate.setDetails(Arrays.asList(sampleTransactionDetailDTO));

        when(adminTransactionService.createTransaction(any(TransactionDTO.class))).thenReturn(sampleTransactionDTO);

        ResponseEntity<TransactionDTO> response = adminTransactionController.createTransaction(transactionToCreate);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionId, response.getBody().getId());
        assertEquals(userId, response.getBody().getUserId());
        verify(adminTransactionService, times(1)).createTransaction(transactionToCreate);
    }

    @Test
    @DisplayName("CP04 - Obtener transacción por ID: con datos existentes")
    void getTransactionById_withExistingId_returnsOkAndTransaction() {
        when(adminTransactionService.getTransactionById(transactionId)).thenReturn(sampleTransactionDTO);

        ResponseEntity<TransactionDTO> response = adminTransactionController.getTransaction(transactionId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionId, response.getBody().getId());
        verify(adminTransactionService, times(1)).getTransactionById(transactionId);
    }

    @Test
    @DisplayName("CP06 - Obtener detalles de transacción: con datos existentes")
    void getTransactionDetails_withExistingData_returnsOkAndList() {
        when(adminTransactionService.getTransactionDetails(transactionId)).thenReturn(List.of(sampleTransactionDetailDTO));

        ResponseEntity<List<TransactionDetailDTO>> response = adminTransactionController.getTransactionDetails(transactionId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(adminTransactionService, times(1)).getTransactionDetails(transactionId);
    }

    @Test
    @DisplayName("CP07 - Obtener detalles de transacción: sin datos")
    void getTransactionDetails_noData_returnsNoContent() {
        when(adminTransactionService.getTransactionDetails(transactionId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<TransactionDetailDTO>> response = adminTransactionController.getTransactionDetails(transactionId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(adminTransactionService, times(1)).getTransactionDetails(transactionId);
    }

    @Test
    @DisplayName("CP08 - Obtener todas las transacciones: con datos existentes")
    void getAllTransactions_withExistingData_returnsOkAndList() {
        when(adminTransactionService.getAllTransactions()).thenReturn(List.of(sampleTransactionDTO));

        ResponseEntity<List<TransactionDTO>> response = adminTransactionController.getAllTransactions();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(transactionId, response.getBody().get(0).getId());
        verify(adminTransactionService, times(1)).getAllTransactions();
    }

    @Test
    @DisplayName("CP09 - Obtener todas las transacciones: sin datos")
    void getAllTransactions_noData_returnsOkAndEmptyList() {
        when(adminTransactionService.getAllTransactions()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TransactionDTO>> response = adminTransactionController.getAllTransactions();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(adminTransactionService, times(1)).getAllTransactions();
    }

    @Test
    @DisplayName("CP10 - Crear transacción: datos inválidos lanza BadRequestException")
    void createTransaction_invalidData_throwsBadRequestException() {
        TransactionDTO invalidTransaction = new TransactionDTO(); // faltan campos requeridos

        when(adminTransactionService.createTransaction(any(TransactionDTO.class)))
                .thenThrow(new BadRequestException("Datos inválidos para la transacción"));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> adminTransactionController.createTransaction(invalidTransaction)
        );

        assertEquals("Datos inválidos para la transacción", exception.getMessage());
        verify(adminTransactionService, times(1)).createTransaction(invalidTransaction);
    }
}
