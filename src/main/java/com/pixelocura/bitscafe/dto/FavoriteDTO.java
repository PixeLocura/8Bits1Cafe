package com.pixelocura.bitscafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Relación de favorito entre un usuario y un juego con detalles del juego.")
public class FavoriteDTO {

    @Schema(description = "ID del usuario", example = "e4a1b2c3-d456-7890-a123-b456c7890123")
    private UUID userId;

    @Schema(description = "ID del juego", example = "f5b2c3d4-e567-8901-b234-c567d8901234")
    private UUID gameId;

    @Schema(description = "Título del juego", example = "Pixel Dungeon")
    private String title;

    @Schema(description = "Nombre del desarrollador", example = "RetroWare Studios")
    private String developerName;

    @Schema(description = "URL de la portada", example = "https://example.com/image.jpg")
    private String coverUrl;

    @Schema(description = "Precio", example = "19.99")
    private Double price;
}
