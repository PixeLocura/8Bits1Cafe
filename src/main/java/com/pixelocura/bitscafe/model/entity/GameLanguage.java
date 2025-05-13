package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "game_languages")
@IdClass(GameLanguage.GameLanguageId.class)
public class GameLanguage {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_game_language_game")
    )
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "language_iso",
            referencedColumnName = "iso_code",
            foreignKey = @ForeignKey(name = "fk_game_language_language")
    )
    private Language language;

    @Data
    public static class GameLanguageId implements Serializable {
        private UUID game;
        private String language;
    }
}
