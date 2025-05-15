package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LanguageDTO {

    @Pattern(regexp = "^[A-Z]{2,3}$")
    @Size(min=2, max=3)
    private String isoCode;

    @NotBlank(message = "Language name is required")
    private String name;
}
