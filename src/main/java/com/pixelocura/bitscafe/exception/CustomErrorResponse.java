package com.pixelocura.bitscafe.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    LocalDateTime ZonedDateTime;
    String message;
    String details;
}
