package com.pixelocura.bitscafe.mapper;


import com.pixelocura.bitscafe.dto.PlatformDTO;
import com.pixelocura.bitscafe.model.entity.Platform;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    private final ModelMapper modelMapper;

    public PlatformMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public PlatformDTO toDTO(Platform platform) { return modelMapper.map(platform, PlatformDTO.class);}

    public Platform toEntity(PlatformDTO platformDTO) {return modelMapper.map(platformDTO, Platform.class);}
}

