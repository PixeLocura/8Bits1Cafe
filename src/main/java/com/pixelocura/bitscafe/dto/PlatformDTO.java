package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class PlatformDTO {

    private UUID id;

    @NotBlank
    private String name;
}
