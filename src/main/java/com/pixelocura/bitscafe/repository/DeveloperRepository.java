package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
    Optional<Developer> findByName(String name);

    boolean existsByName(String name);

    Page<Developer> findAll(Pageable pageable);

    boolean existsByNameAndUser_IdNot(String name, UUID userId);
}
