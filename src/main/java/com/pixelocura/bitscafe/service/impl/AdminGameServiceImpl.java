package com.pixelocura.bitscafe.service.impl;
import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.mapper.GameMapper;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.service.AdminGameService;
import com.pixelocura.bitscafe.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AdminGameServiceImpl implements AdminGameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private static final Logger log = LoggerFactory.getLogger(AdminGameServiceImpl.class);

    @Override
    public List<GameDTO> findAll() {
        return gameRepository.findAll().stream().map(gameMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<GameDTO> paginate(Pageable pageable) {

        return gameRepository.findAll(pageable).map(gameMapper::toDTO);
    }

    @Override
    public GameDTO create(GameDTO game) {
        log.debug("Incoming DTO → title={}, coverUrl={}", game.getTitle(), game.getCoverUrl());
        Game gameEntity = gameMapper.toEntity(game);


        if (gameRepository.existsByTitle(gameEntity.getTitle())) {
            throw new IllegalArgumentException("Ya existe un juego con ese título.");
        }

        Game savedGame = gameRepository.save(gameEntity);
        return gameMapper.toDTO(savedGame);
    }

    @Override
    public GameDTO findById(UUID id) {
        Game game = gameRepository.findById(id).orElse(null);
        return gameMapper.toDTO(game);
    }

    @Override
    public GameDTO update(UUID id, GameDTO updatedGame) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado con ID: " + id));

        existingGame.setTitle(updatedGame.getTitle());
        existingGame.setDescription(updatedGame.getDescription());
        existingGame.setPrice(updatedGame.getPrice());
        existingGame.setCoverUrl(updatedGame.getCoverUrl());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());

        Game savedGame = gameRepository.save(existingGame);
        return gameMapper.toDTO(savedGame);

    }

    @Override
    public void delete(UUID id) {
        if (!gameRepository.existsById(id)) {
            throw new IllegalArgumentException("Juego no encontrado con ID: " + id);
        }
        gameRepository.deleteById(id);

    }
}
