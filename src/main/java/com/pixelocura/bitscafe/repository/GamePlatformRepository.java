package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GamePlatform;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GamePlatformRepository extends JpaRepository<GamePlatform, GamePlatform.GamePlatformId> {
}
