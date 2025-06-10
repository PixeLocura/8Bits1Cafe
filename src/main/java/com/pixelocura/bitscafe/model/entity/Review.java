package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "reviews")
@IdClass(Review.ReviewId.class)
public class Review {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_review_user")
    )
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_review_game")
    )
    private Game game;

    @Min(0)
    @Max(5)
    @Column(nullable = false)
    private Double rating;

    @Size(max = 2000)
    private String comment;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now(ZoneOffset.UTC);

    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @Data
    public static class ReviewId implements Serializable {
        private UUID user;
        private UUID game;
    }
}
