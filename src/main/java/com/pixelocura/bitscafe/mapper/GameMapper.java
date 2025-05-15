package com.pixelocura.bitscafe.mapper;
import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.model.entity.Game;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class GameMapper {

    public static GameDTO toDTO(Game game) {
        if (game == null) return null;

        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setPrice(game.getPrice());
        dto.setCoverUrl(game.getCoverUrl());

        if (game.getReleaseDate() == null) {
            dto.setReleaseDate(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            dto.setReleaseDate(game.getReleaseDate());
        }

        if (game.getCreationDate() == null) {
            dto.setCreationDate(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            dto.setCreationDate(game.getCreationDate());
        }

        dto.setUpdateDate(game.getUpdateDate());

        return dto;
    }

    public static Game toEntity(GameDTO dto) {
        if (dto == null) return null;

        Game game = new Game();
        game.setId(dto.getId());
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setCoverUrl(dto.getCoverUrl());
        game.setReleaseDate(dto.getReleaseDate());
        if(game.getCreationDate() == null){
            game.setCreationDate(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            game.setCreationDate(game.getCreationDate());
        }
        if(game.getUpdateDate() == null){
            game.setUpdateDate(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            game.setUpdateDate(game.getUpdateDate());
        }


        return game;
    }

    public static List<GameDTO> toDTOList(List<Game> games) {
        return games.stream()
                .map(GameMapper::toDTO)
                .collect(Collectors.toList());
    }
}


