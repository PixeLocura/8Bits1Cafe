package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.LanguageDTO;
import com.pixelocura.bitscafe.model.entity.Language;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {
    private final ModelMapper modelMapper;

    public LanguageMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public LanguageDTO toDTO(Language language) { return modelMapper.map(language, LanguageDTO.class);}

    public Language toEntity(LanguageDTO languageDTO) {return modelMapper.map(languageDTO, Language.class);}

}
