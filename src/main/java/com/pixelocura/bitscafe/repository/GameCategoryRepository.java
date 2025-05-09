package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameCategory;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameCategoryRepository extends JpaRepository<GameCategory, GameCategory.GameCategoryId> {
}
