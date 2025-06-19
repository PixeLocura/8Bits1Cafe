package com.pixelocura.bitscafe.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class GameMediaDTO {
    private UUID id;
    private UUID gameId;
    private String mediaLink;
    private ZonedDateTime creationDate;
}
