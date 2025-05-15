package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.model.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByDeveloperProfile(Developer developer);

    @Modifying
    @Query(value = "UPDATE users SET developer_profile = :developerId WHERE id = :userId", nativeQuery = true)
    void updateDeveloperProfile(@Param("userId") UUID userId, @Param("developerId") UUID developerId);

    @Modifying
    @Query(value = "UPDATE users SET developer_profile = NULL WHERE id = :userId", nativeQuery = true)
    void removeDeveloperProfile(@Param("userId") UUID userId);
}
