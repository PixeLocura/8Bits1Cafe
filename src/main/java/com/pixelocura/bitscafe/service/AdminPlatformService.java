package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.PlatformDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminPlatformService {
    List<PlatformDTO> findAll();
    Page<PlatformDTO> paginate(Pageable pageable);
    PlatformDTO create(PlatformDTO platform);
    PlatformDTO findById(UUID id);
    PlatformDTO update(UUID id, PlatformDTO updatedPlatform);
    void delete(UUID id);
}
