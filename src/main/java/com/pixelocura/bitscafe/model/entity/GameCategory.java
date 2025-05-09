package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "game_categories")
@IdClass(GameCategory.GameCategoryId.class)
public class GameCategory {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_game_category_game")
    )
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_game_category_category")
    )
    private Category category;

    @Data
    public static class GameCategoryId implements Serializable {
        private UUID game;
        private UUID category;
    }
}
