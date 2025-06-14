package com.pixelocura.bitscafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Detalle individual de una transacción, incluyendo el juego y su precio.")
public class TransactionDetailDTO {

    @Schema(description = "ID de la transacción asociada", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID transactionId;

    @Schema(description = "ID del juego comprado", example = "b134f1dd-5a44-4a7c-a6a1-9d0955bda233")
    private UUID gameId;

    @Schema(description = "Precio del juego al momento de la transacción", example = "59.99")
    private Double price;
}
