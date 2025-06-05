package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameLanguageRepository extends JpaRepository<GameLanguage, GameLanguage.GameLanguageId> {
}
