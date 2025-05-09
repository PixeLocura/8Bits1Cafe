package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "game_media")
public class GameMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_game_media_game")
    )
    private Game game;

    @Column(name = "media_link", nullable = false)
    private String mediaLink;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
