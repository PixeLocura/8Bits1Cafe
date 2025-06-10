package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GamePlatform;
import com.pixelocura.bitscafe.model.enums.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GamePlatformRepository extends JpaRepository<GamePlatform, GamePlatform.GamePlatformId> {
    List<GamePlatform> findByGameId(UUID gameId);

    List<GamePlatform> findByPlatform(Platform platform);
}
