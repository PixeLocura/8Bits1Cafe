package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Platform;
import com.pixelocura.bitscafe.service.AdminPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminPlatformServiceImpl implements AdminPlatformService {
    @Override
    public List<Platform> findAll() {
        return List.of();
    }

    @Override
    public Page<Platform> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Platform create(Platform platform) {
        return null;
    }

    @Override
    public Platform findById(UUID id) {
        return null;
    }

    @Override
    public Platform update(UUID id, Platform updatedPlatform) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
