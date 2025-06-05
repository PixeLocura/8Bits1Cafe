package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCategoryRepository extends JpaRepository<GameCategory, GameCategory.GameCategoryId> {
}
