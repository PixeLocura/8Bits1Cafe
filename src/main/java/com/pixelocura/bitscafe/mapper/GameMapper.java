package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.GamePlatform;
import com.pixelocura.bitscafe.model.enums.Platform;
import com.pixelocura.bitscafe.repository.DeveloperRepository;
import com.pixelocura.bitscafe.repository.GamePlatformRepository;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    private final DeveloperRepository developerRepository;
    private final GamePlatformRepository gamePlatformRepository;

    public GameMapper(DeveloperRepository developerRepository, GamePlatformRepository gamePlatformRepository) {
        this.developerRepository = developerRepository;
        this.gamePlatformRepository = gamePlatformRepository;
    }

    public GameDTO toDTO(Game game) {
        if (game == null)
            return null;

        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setPrice(game.getPrice());
        dto.setCoverUrl(game.getCoverUrl());
        dto.setCreationDate(game.getCreationDate());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setDeveloper_id(game.getDeveloper().getId());
        dto.setUpdateDate(game.getUpdateDate());

        List<Platform> platforms = gamePlatformRepository.findByGameId(game.getId())
                .stream()
                .map(GamePlatform::getPlatform)
                .collect(Collectors.toList());

        dto.setPlatforms(platforms);

        return dto;
    }

    public Game toEntity(GameDTO dto) {
        if (dto == null)
            return null;

        Game game = new Game();
        game.setId(dto.getId());
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setCoverUrl(dto.getCoverUrl());
        game.setReleaseDate(dto.getReleaseDate());
        if (game.getCreationDate() == null) {
            game.setCreationDate(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            game.setCreationDate(game.getCreationDate());
        }
        if (game.getUpdateDate() == null) {
            game.setUpdateDate(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            game.setUpdateDate(game.getUpdateDate());
        }

        Developer developer = developerRepository.findById(dto.getDeveloper_id())
                .orElseThrow(() -> new RuntimeException("Developer not found with id: " + dto.getDeveloper_id()));
        game.setDeveloper(developer);
        return game;
    }

    public void savePlatformsForGame(Game game, List<Platform> platforms) {
        if (platforms == null) {
            return;
        }

        List<GamePlatform> existingPlatforms = gamePlatformRepository.findByGameId(game.getId());
        gamePlatformRepository.deleteAll(existingPlatforms);

        if (!platforms.isEmpty()) {
            List<GamePlatform> gamePlatforms = platforms.stream()
                    .map(platform -> {
                        GamePlatform gamePlatform = new GamePlatform();
                        gamePlatform.setGame(game);
                        gamePlatform.setPlatform(platform);
                        return gamePlatform;
                    })
                    .collect(Collectors.toList());

            gamePlatformRepository.saveAll(gamePlatforms);
        }
    }
}
