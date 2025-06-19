package com.pixelocura.bitscafe.dto;

import lombok.Data;
@Data
public class AuthResponseDTO {
    private String token; // El token JWT
    private String name; // El primer nombre del usuario
    private String role; // El rol del usuario (e.g., ADMIN,DEVELOPER, OTRO)
}
