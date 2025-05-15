package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.CountryDTO;
import com.pixelocura.bitscafe.mapper.CountryMapper;
import com.pixelocura.bitscafe.model.entity.Country;
import com.pixelocura.bitscafe.repository.CountryRepository;
import com.pixelocura.bitscafe.service.AdminCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminCountryServiceImpl implements AdminCountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CountryDTO> findAll() {
        return countryRepository.findAll().stream()
                .map(countryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryDTO> paginate(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(countryMapper::toDTO);
    }

    @Override
    @Transactional
    public CountryDTO create(CountryDTO countryDTO) {
        Country country = countryMapper.toEntity(countryDTO);
        Country savedCountry = countryRepository.save(country);
        return countryMapper.toDTO(savedCountry);
    }

    @Override
    @Transactional(readOnly = true)
    public CountryDTO findByIsoCode(String isoCode) {
        Country country = countryRepository.findByIsoCode(isoCode)
                .orElseThrow(() -> new RuntimeException("Country not found with ISO code: " + isoCode));
        return countryMapper.toDTO(country);
    }

    @Override
    @Transactional
    public CountryDTO update(String isoCode, CountryDTO countryDTO) {
        Country countryEntity = countryRepository.findByIsoCode(isoCode)
                .orElseThrow(() -> new RuntimeException("Country not found with ISO code: " + isoCode));

        if (countryDTO.getName() != null) {
            countryEntity.setName(countryDTO.getName());
        }
        if (countryDTO.getFlagUrl() != null) {
            countryEntity.setFlagUrl(countryDTO.getFlagUrl());
        }

        Country updatedCountry = countryRepository.save(countryEntity);
        return countryMapper.toDTO(updatedCountry);
    }

    @Override
    @Transactional
    public void delete(String isoCode) {
        Country countryToDelete = countryRepository.findByIsoCode(isoCode)
                .orElseThrow(() -> new RuntimeException("Country not found with ISO code: " + isoCode));

        countryRepository.delete(countryToDelete);
    }
}
