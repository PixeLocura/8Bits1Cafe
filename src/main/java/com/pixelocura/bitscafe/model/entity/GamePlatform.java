package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "game_platforms")
@IdClass(GamePlatform.GamePlatformId.class)
public class GamePlatform {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_game_platform_game")
    )
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "platform_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_game_platform_platform")
    )
    private Platform platform;

    @Data
    public static class GamePlatformId implements Serializable {
        private UUID game;
        private UUID platform;
    }
}
