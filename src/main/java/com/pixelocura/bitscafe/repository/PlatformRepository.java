package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PlatformRepository extends JpaRepository<Platform, UUID> {
}
