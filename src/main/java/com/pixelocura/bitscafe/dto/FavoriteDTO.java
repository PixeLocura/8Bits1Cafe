package com.pixelocura.bitscafe.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FavoriteDTO {
    private UUID userId;
    private UUID gameId;
}
