package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    boolean existsByTitle(String title);

    List<Game> findByDeveloperId(UUID developerId);
}
