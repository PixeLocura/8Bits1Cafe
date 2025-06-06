package com.pixelocura.bitscafe.dto;

import com.pixelocura.bitscafe.model.enums.Category;
import com.pixelocura.bitscafe.model.enums.Language;
import com.pixelocura.bitscafe.model.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
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

    private List<Platform> platforms;

    private List<Category> categories;

    private List<Language> languages;

    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;
}
