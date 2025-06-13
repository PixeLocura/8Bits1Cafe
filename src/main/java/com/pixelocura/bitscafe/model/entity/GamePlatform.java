package com.pixelocura.bitscafe.model.entity;

import com.pixelocura.bitscafe.model.enums.Platform;
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
    @JoinColumn(name = "game_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_game_platform_game"))
    private Game game;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "platform_name")
    private Platform platform;

    @Data
    public static class GamePlatformId implements Serializable {
        private UUID game;
        private Platform platform;
    }
}
