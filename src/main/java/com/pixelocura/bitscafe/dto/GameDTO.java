package com.pixelocura.bitscafe.dto;

import com.pixelocura.bitscafe.model.enums.Category;
import com.pixelocura.bitscafe.model.enums.Language;
import com.pixelocura.bitscafe.model.enums.Platform;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class GameDTO {
    private UUID id;

    @NotNull(message = "Developer ID is required")
    private UUID developer_id;

    @NotBlank(message = "Error")
    private String title;

    @NotBlank(message = "Error")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be at least 0")
    private Double price;

    @NotBlank(message = "Cover URL is required")
    @URL(message = "Cover URL must be valid")
    private String coverUrl;

    @NotNull(message = "Error")
    private ZonedDateTime releaseDate;

    private List<Platform> platforms;

    private List<Category> categories;

    private List<Language> languages;

    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;
}
