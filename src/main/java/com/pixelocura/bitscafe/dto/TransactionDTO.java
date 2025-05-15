// TransactionDTO.java
package com.pixelocura.bitscafe.dto;

import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TransactionDTO {
    private UUID id;
    private UUID userId;
    private Double totalPrice;
    private ZonedDateTime transactionDate;
    private List<TransactionDetailDTO> details;
}
