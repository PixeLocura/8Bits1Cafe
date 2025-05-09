package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.GameMedia;
import com.pixelocura.bitscafe.service.AdminGameMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminGameMediaServiceImpl implements AdminGameMediaService {
    @Override
    public List<GameMedia> findAll() {
        return List.of();
    }

    @Override
    public Page<GameMedia> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public GameMedia create(GameMedia gameMedia) {
        return null;
    }

    @Override
    public GameMedia findById(UUID id) {
        return null;
    }

    @Override
    public GameMedia update(UUID id, GameMedia updatedGameMedia) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
