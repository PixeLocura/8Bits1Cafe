package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.model.entity.Developer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeveloperMapper {
    private final ModelMapper modelMapper;

    public DeveloperDTO toDTO(Developer developer) {
        DeveloperDTO dto = modelMapper.map(developer, DeveloperDTO.class);
        // Map games to GameDTO if games are present
        if (developer.getGames() != null) {
            List<GameDTO> gameDTOs = developer.getGames().stream()
                    .map(game -> modelMapper.map(game, GameDTO.class))
                    .toList();
            dto.setGames(gameDTOs);
        }
        return dto;
    }

    public Developer toEntity(DeveloperDTO developerDTO) {
        return modelMapper.map(developerDTO, Developer.class);
    }

    public void updateEntity(DeveloperDTO developerDTO, Developer developer) {
        modelMapper.map(developerDTO, developer);
    }
}
