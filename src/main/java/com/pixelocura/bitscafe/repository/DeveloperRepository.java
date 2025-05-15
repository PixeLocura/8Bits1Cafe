package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
    Optional<Developer> findByName(String name);
}
