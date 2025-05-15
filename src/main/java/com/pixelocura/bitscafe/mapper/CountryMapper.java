package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.CountryDTO;
import com.pixelocura.bitscafe.model.entity.Country;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    private final ModelMapper modelMapper;

    public CountryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//  Entity -> DTO
    public CountryDTO toDTO(Country country) {
        return modelMapper.map(country, CountryDTO.class);
    }

//  DTO -> Entity
    public Country toEntity(CountryDTO countryDTO) {
        return modelMapper.map(countryDTO, Country.class);
    }
}