package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.model.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByDeveloperProfile(Developer developer);
}
