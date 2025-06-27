package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.service.AdminTransactionService;
import com.pixelocura.bitscafe.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transaction", description = "Gestión de transacciones realizadas en la plataforma")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    public AdminTransactionController(AdminTransactionService transactionService) {
        this.adminTransactionService = transactionService;
    }

    @Operation(summary = "Listar transacciones por usuario", description = "Devuelve una lista de transacciones realizadas por un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacciones encontradas"),
            @ApiResponse(responseCode = "204", description = "No se encontraron transacciones")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUser(@PathVariable UUID userId) {
        List<TransactionDTO> transactions = adminTransactionService.getTransactionsByUser(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Crear una nueva transacción", description = "Registra una nueva transacción en la plataforma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class)))
    })
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO created = adminTransactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Obtener una transacción por ID", description = "Devuelve la información de una transacción específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable UUID id) {
        TransactionDTO transactionDTO = adminTransactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }

    @Operation(summary = "Obtener detalles de una transacción", description = "Devuelve la lista de ítems o detalles asociados a una transacción específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de transacción encontrados"),
            @ApiResponse(responseCode = "204", description = "La transacción no tiene detalles")
    })
    @GetMapping("/{transactionId}/details")
    public ResponseEntity<List<TransactionDetailDTO>> getTransactionDetails(@PathVariable UUID transactionId) {
        List<TransactionDetailDTO> details = adminTransactionService.getTransactionDetails(transactionId);
        if (details.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(details);
    }

    @Operation(summary = "Listar todas las transacciones", description = "Devuelve una lista de todas las transacciones registradas en la plataforma.")
    @ApiResponse(responseCode = "200", description = "Transacciones encontradas")
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> list = adminTransactionService.getAllTransactions();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Crear transacción de juegos para usuario autenticado", description = "Crea una transacción para el usuario autenticado usando una lista de IDs de juegos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/games")
    public ResponseEntity<TransactionDTO> createTransactionForGames(
            @RequestBody List<UUID> gameIds,
            @AuthenticationPrincipal UserPrincipal userDetails) {
        UUID userId = userDetails.getId();
        TransactionDTO created = adminTransactionService.createTransactionForUserAndGames(userId, gameIds);
        return ResponseEntity.ok(created);
    }
}
