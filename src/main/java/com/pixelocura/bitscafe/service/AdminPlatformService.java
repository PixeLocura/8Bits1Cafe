package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminPlatformService {
    List<Platform> findAll();
    Page<Platform> paginate(Pageable pageable);
    Platform create(Platform platform);
    Platform findById(UUID id);
    Platform update(UUID id, Platform updatedPlatform);
    void delete(UUID id);
}
