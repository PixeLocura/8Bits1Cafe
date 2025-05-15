// TransactionMapper.java
package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.TransactionDTO;
import com.pixelocura.bitscafe.dto.TransactionDetailDTO;
import com.pixelocura.bitscafe.model.entity.Transaction;
import com.pixelocura.bitscafe.model.entity.TransactionDetail;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {

    public static TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;

        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setUserId(transaction.getUser().getId());
        dto.setTotalPrice(transaction.getTotalPrice());
        dto.setTransactionDate(transaction.getTransactionDate());

        if (transaction.getDetails() != null) {
            List<TransactionDetailDTO> detailsDTO = transaction.getDetails()
                    .stream()
                    .map(TransactionMapper::toDetailDTO)
                    .collect(Collectors.toList());
            dto.setDetails(detailsDTO);
        }
        return dto;
    }

    public static TransactionDetailDTO toDetailDTO(TransactionDetail detail) {
        if (detail == null) return null;

        TransactionDetailDTO dto = new TransactionDetailDTO();
        dto.setTransactionId(detail.getTransaction().getId());
        dto.setGameId(detail.getGame().getId());
        dto.setPrice(detail.getPrice());
        return dto;
    }

    public static List<TransactionDTO> toTransactionDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<TransactionDetailDTO> toTransactionDetailDTOList(List<TransactionDetail> details) {
        return details.stream()
                .map(TransactionMapper::toDetailDTO)
                .collect(Collectors.toList());
    }


}
