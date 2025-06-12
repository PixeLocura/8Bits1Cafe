package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.GameMediaDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.GameMedia;
import com.pixelocura.bitscafe.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMediaMapper {

    private final GameRepository gameRepository;

    public GameMediaDTO toDTO(GameMedia entity) {
        GameMediaDTO dto = new GameMediaDTO();
        dto.setId(entity.getId());
        dto.setGameId(entity.getGame().getId());
        dto.setMediaLink(entity.getMediaLink());
        dto.setCreationDate(entity.getCreationDate());
        return dto;
    }

    public GameMedia toEntity(GameMediaDTO dto) {
        Game game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        GameMedia entity = new GameMedia();
        entity.setId(dto.getId());
        entity.setGame(game);
        entity.setMediaLink(dto.getMediaLink());
        entity.setCreationDate(dto.getCreationDate());
        return entity;
    }
}
