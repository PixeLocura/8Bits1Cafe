package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.GameCategory;
import com.pixelocura.bitscafe.model.entity.GameLanguage;
import com.pixelocura.bitscafe.model.entity.GamePlatform;
import com.pixelocura.bitscafe.model.enums.Category;
import com.pixelocura.bitscafe.model.enums.Language;
import com.pixelocura.bitscafe.model.enums.Platform;
import com.pixelocura.bitscafe.repository.DeveloperRepository;
import com.pixelocura.bitscafe.repository.GameCategoryRepository;
import com.pixelocura.bitscafe.repository.GameLanguageRepository;
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
    private final GameCategoryRepository gameCategoryRepository;
    private final GameLanguageRepository gameLanguageRepository;

    public GameMapper(DeveloperRepository developerRepository,
            GamePlatformRepository gamePlatformRepository,
            GameCategoryRepository gameCategoryRepository,
            GameLanguageRepository gameLanguageRepository) {
        this.developerRepository = developerRepository;
        this.gamePlatformRepository = gamePlatformRepository;
        this.gameCategoryRepository = gameCategoryRepository;
        this.gameLanguageRepository = gameLanguageRepository;
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

        List<Category> categories = gameCategoryRepository.findByGameId(game.getId())
                .stream()
                .map(GameCategory::getCategory)
                .collect(Collectors.toList());

        dto.setCategories(categories);

        List<Language> languages = gameLanguageRepository.findByGameId(game.getId())
                .stream()
                .map(GameLanguage::getLanguage)
                .collect(Collectors.toList());

        dto.setLanguages(languages);

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

    public void saveCategoriesForGame(Game game, List<Category> categories) {
        if (categories == null) {
            return;
        }

        List<GameCategory> existingCategories = gameCategoryRepository.findByGameId(game.getId());
        gameCategoryRepository.deleteAll(existingCategories);

        if (!categories.isEmpty()) {
            List<GameCategory> gameCategories = categories.stream()
                    .map(category -> {
                        GameCategory gameCategory = new GameCategory();
                        gameCategory.setGame(game);
                        gameCategory.setCategory(category);
                        return gameCategory;
                    })
                    .collect(Collectors.toList());

            gameCategoryRepository.saveAll(gameCategories);
        }
    }

    public void saveLanguagesForGame(Game game, List<Language> languages) {
        if (languages == null) {
            return;
        }

        List<GameLanguage> existingLanguages = gameLanguageRepository.findByGameId(game.getId());
        gameLanguageRepository.deleteAll(existingLanguages);

        if (!languages.isEmpty()) {
            List<GameLanguage> gameLanguages = languages.stream()
                    .map(language -> {
                        GameLanguage gameLanguage = new GameLanguage();
                        gameLanguage.setGame(game);
                        gameLanguage.setLanguage(language);
                        return gameLanguage;
                    })
                    .collect(Collectors.toList());

            gameLanguageRepository.saveAll(gameLanguages);
        }
    }
}
