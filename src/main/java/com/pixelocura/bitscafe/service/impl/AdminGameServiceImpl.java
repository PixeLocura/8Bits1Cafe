package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.service.AdminGameService;
import com.pixelocura.bitscafe.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AdminGameServiceImpl implements AdminGameService {
    private final GameRepository gameRepository;
    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Page<Game> paginate(Pageable pageable) {

        return gameRepository.findAll(pageable);
    }

    @Override
    public Game create(Game game) {
        if (gameRepository.existsByTitle(game.getTitle())) {
            throw new IllegalArgumentException("Ya existe un juego con ese título.");
        }
        return gameRepository.save(game);
    }

    @Override
    public Game findById(UUID id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Override
    public Game update(UUID id, Game updatedGame) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado con ID: " + id));

        existingGame.setTitle(updatedGame.getTitle());
        existingGame.setDescription(updatedGame.getDescription());
        existingGame.setPrice(updatedGame.getPrice());
        existingGame.setCoverUrl(updatedGame.getCoverUrl());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());
        existingGame.setDeveloper(updatedGame.getDeveloper());

        return gameRepository.save(existingGame);

    }

    @Override
    public void delete(UUID id) {
        if (!gameRepository.existsById(id)) {
            throw new IllegalArgumentException("Juego no encontrado con ID: " + id);
        }
        gameRepository.deleteById(id);

    }
}
