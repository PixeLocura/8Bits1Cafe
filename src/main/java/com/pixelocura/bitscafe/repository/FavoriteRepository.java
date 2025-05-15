package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Favorite;
import com.pixelocura.bitscafe.model.entity.Favorite.FavoriteId;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findByUser_Id(UUID userId);
    Optional<Favorite> findByUser_IdAndGame_Id(UUID userId, UUID gameId);
    void deleteByUser_IdAndGame_Id(UUID userId, UUID gameId);
    boolean existsByUser_IdAndGame_Id(UUID userId, UUID gameId);

}
