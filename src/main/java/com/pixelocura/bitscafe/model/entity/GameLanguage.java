package com.pixelocura.bitscafe.model.entity;

import com.pixelocura.bitscafe.model.enums.Language;
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
    @JoinColumn(name = "game_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_game_language_game"))
    private Game game;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "language_name")
    private Language language;

    @Data
    public static class GameLanguageId implements Serializable {
        private UUID game;
        private Language language;
    }
}
