package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameLanguageRepository extends JpaRepository<GameLanguage, GameLanguage.GameLanguageId> {
    List<GameLanguage> findByGameId(UUID gameId);
}
