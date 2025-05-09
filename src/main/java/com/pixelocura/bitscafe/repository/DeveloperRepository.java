package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
}
