package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.model.entity.Developer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeveloperMapper {
    private final ModelMapper modelMapper;

    public DeveloperDTO toDTO(Developer developer) {
        return modelMapper.map(developer, DeveloperDTO.class);
    }

    public Developer toEntity(DeveloperDTO developerDTO) {
        return modelMapper.map(developerDTO, Developer.class);
    }

    public void updateEntity(DeveloperDTO developerDTO, Developer developer) {
        modelMapper.map(developerDTO, developer);
    }
}
