package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "transaction_details")
@IdClass(TransactionDetail.TransactionDetailId.class)
public class TransactionDetail {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "transaction_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_transaction_detail_transaction")
    )
    private Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_transaction_detail_game")
    )
    private Game game;

    @Column(nullable = false)
    @Min(0)
    private Double price;

    @Data
    public static class TransactionDetailId implements Serializable {
        private UUID transaction;
        private UUID game;
    }
}
