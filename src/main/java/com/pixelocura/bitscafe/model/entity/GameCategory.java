package com.pixelocura.bitscafe.model.entity;

import com.pixelocura.bitscafe.model.enums.Category;
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
    @JoinColumn(name = "game_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_game_category_game"))
    private Game game;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private Category category;

    @Data
    public static class GameCategoryId implements Serializable {
        private UUID game;
        private Category category;
    }
}
