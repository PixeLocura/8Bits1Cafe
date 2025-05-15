package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class ReviewDTO {
    private UUID userId;
    private UUID gameId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String comment;
}
