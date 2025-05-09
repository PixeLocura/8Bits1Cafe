package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.service.AdminGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminGameServiceImpl implements AdminGameService {
    @Override
    public List<Game> findAll() {
        return List.of();
    }

    @Override
    public Page<Game> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Game create(Game game) {
        return null;
    }

    @Override
    public Game findById(UUID id) {
        return null;
    }

    @Override
    public Game update(UUID id, Game updatedGame) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
