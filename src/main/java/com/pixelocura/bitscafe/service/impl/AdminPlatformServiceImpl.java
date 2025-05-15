package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.PlatformDTO;
import com.pixelocura.bitscafe.exception.BadRequestException;
import com.pixelocura.bitscafe.mapper.PlatformMapper;
import com.pixelocura.bitscafe.model.entity.Platform;
import com.pixelocura.bitscafe.repository.PlatformRepository;
import com.pixelocura.bitscafe.service.AdminPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminPlatformServiceImpl implements AdminPlatformService {
    private final PlatformRepository platformRepository;
    private final PlatformMapper platformMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PlatformDTO> findAll() {
        return platformRepository.findAll().stream()
                .map(platformMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlatformDTO> paginate(Pageable pageable) {
        return platformRepository.findAll(pageable)
                .map(platformMapper::toDTO);
    }

    @Override
    @Transactional
    public PlatformDTO create(PlatformDTO platformDTO) {
        Platform platform = platformMapper.toEntity(platformDTO);
        Platform savedPlatform = platformRepository.save(platform);
        return platformMapper.toDTO(savedPlatform);
    }

    @Override
    @Transactional(readOnly = true)
    public PlatformDTO findById(UUID id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Platform not found with ID: " + id.toString()));
        return platformMapper.toDTO(platform);
    }

    @Override
    @Transactional
    public PlatformDTO update(UUID id, PlatformDTO platform) {
        Platform platformEntity = platformRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Platform not found with ID: " + id.toString()));
        if(platform.getName() != null){
            platformEntity.setName(platform.getName());
        }
        Platform updatedPlatform = platformRepository.save(platformEntity);
        return platformMapper.toDTO(updatedPlatform);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Platform platformToDelete = platformRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Platform not found with ID: " + id.toString()));

        platformRepository.delete(platformToDelete);
    }
}
