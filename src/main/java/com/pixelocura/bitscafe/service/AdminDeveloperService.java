package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminDeveloperService {
    List<DeveloperDTO> findAll();

    Page<DeveloperDTO> paginate(Pageable pageable);

    DeveloperDTO create(DeveloperDTO developerDTO);

    DeveloperDTO findById(UUID id);

    DeveloperDTO findByName(String name);

    DeveloperDTO update(UUID id, DeveloperDTO developerDTO);

    void delete(UUID id);

    DeveloperDTO createDeveloperProfile(DeveloperDTO developerDTO, UUID userId);

    /**
     * Checks if a user has a developer profile.
     * 
     * @param userId the UUID of the user
     * @return true if the user has a developer profile, false otherwise
     */
    boolean hasDeveloperProfile(UUID userId);
}
