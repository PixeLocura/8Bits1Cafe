package com.pixelocura.bitscafe.dto;

import com.pixelocura.bitscafe.model.enums.ERole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserProfileDTO {
    // Developer - Attributes -------
    private UUID id;
    private String name;
    private ERole role; // El Rol puede ser Customer u otro
    // User - Attributes -------
    private String email;
    private String profilePictureUrl;
}

