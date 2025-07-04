package com.pixelocura.bitscafe.dto;

import com.pixelocura.bitscafe.model.enums.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class DeveloperDTO {
    private UUID id;

    @NotBlank(message = "Developer name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Developer description is required")
    private String description;

    @URL(message = "Website must be a valid URL")
    private String website;

    private ZonedDateTime creationDate;

    // Add games list for developer
    private List<GameDTO> games;

    private Country country;

    @URL(message = "Profile picture must be a valid URL")
    private String profilePictureUrl;
}
