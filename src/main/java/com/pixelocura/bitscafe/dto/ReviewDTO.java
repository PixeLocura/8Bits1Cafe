package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class ReviewDTO {

    private UUID id;

    @NotNull(message = "User ID is required")
    private UUID userId;  // Para el ID del usuario
    @NotNull(message = "Game ID is required")
    private UUID gameId;  // Para el ID del juego

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5")
    private Double rating;  // Cambiado a Double para matchear la entidad

    @Size(max = 2000, message = "Comment must be less than 2000 characters")
    private String comment;

    private String userName;
    private String gameTitle;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}