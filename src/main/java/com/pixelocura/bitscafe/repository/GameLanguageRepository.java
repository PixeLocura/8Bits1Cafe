package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameLanguage;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameLanguageRepository extends JpaRepository<GameLanguage, GameLanguage.GameLanguageId> {
}
