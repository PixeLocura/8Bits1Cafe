package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class GameDTO {
    private UUID id;

    @NotBlank(message = "Developer ID is required")
    private UUID developer_id;

    @NotBlank(message = "Error")
    private String title;

    @NotBlank(message = "Error")
    private String description;

    @NotBlank(message = "Error")
    private Double price;

    @NotBlank(message = "Error")
    private String coverUrl;

    @NotBlank(message = "Error")
    private ZonedDateTime releaseDate;

    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;
}
