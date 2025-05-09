package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.GameMedia;
import com.pixelocura.bitscafe.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GameMediaRepository extends JpaRepository<GameMedia, UUID> {
}
