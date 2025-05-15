// TransactionDetailDTO.java
package com.pixelocura.bitscafe.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TransactionDetailDTO {
    private UUID transactionId;
    private UUID gameId;
    private Double price;
}
