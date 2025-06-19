package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_user")
    )
    private User user;

    @Column(name = "total_price", nullable = false)
    @Min(0)
    private Double totalPrice;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> details;

    @Column(name = "transaction_date", nullable = false)
    private ZonedDateTime transactionDate;

    @PrePersist
    public void prePersist() {
        this.transactionDate = ZonedDateTime.now(ZoneOffset.UTC);
        if (this.details != null) {
            this.totalPrice = this.details.stream()
                    .mapToDouble(TransactionDetail::getPrice)
                    .sum();
        }
    }
}
