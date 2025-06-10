package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameCategory;
import com.pixelocura.bitscafe.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GameCategoryRepository extends JpaRepository<GameCategory, GameCategory.GameCategoryId> {
    List<GameCategory> findByGameId(UUID gameId);

    List<GameCategory> findByCategory(Category category);
}
