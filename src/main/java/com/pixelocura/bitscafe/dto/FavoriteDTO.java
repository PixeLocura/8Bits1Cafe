package com.pixelocura.bitscafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Relación de favorito con información de juego.")
public class FavoriteDTO {

    @Schema(description = "ID del usuario que añadió el favorito")
    private UUID userId;

    @Schema(description = "ID del juego marcado como favorito")
    private UUID gameId;

    @Schema(description = "Título del juego")
    private String title;

    @Schema(description = "Desarrollador")
    private String developer;

    @Schema(description = "Imagen de portada")
    private String coverImage;

    @Schema(description = "Puntuación")
    private Double rating;

    @Schema(description = "Categorías")
    private List<String> categories;

    @Schema(description = "Plataformas")
    private List<String> platforms;

    @Schema(description = "Precio")
    private String price;
}
