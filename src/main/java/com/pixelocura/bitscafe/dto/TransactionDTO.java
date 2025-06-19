package com.pixelocura.bitscafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Representación completa de una transacción realizada por un usuario.")
public class TransactionDTO {

    @Schema(description = "ID único de la transacción", example = "c782f3a2-8f42-47ae-8a84-829c0fffa2b1")
    private UUID id;

    @Schema(description = "ID del usuario que realizó la transacción", example = "ab12cd34-ef56-7890-ab12-cd34ef567890")
    private UUID userId;

    @Schema(description = "Monto total de la transacción", example = "59.99")
    private Double totalPrice;

    @Schema(description = "Fecha y hora en que se realizó la transacción", example = "2024-06-10T14:48:00Z")
    private ZonedDateTime transactionDate;

    @Schema(description = "Lista de detalles de la transacción")
    private List<TransactionDetailDTO> details;
}
