package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class ReviewDTO {
    private UUID userId;  // Para el ID del usuario
    private UUID gameId;  // Para el ID del juego

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    private Double rating;  // Cambiado a Double para matchear la entidad

    @Size(max = 2000, message = "Comment must be less than 2000 characters")
    private String comment;
}