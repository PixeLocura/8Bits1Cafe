package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Country;
import com.pixelocura.bitscafe.repository.CountryRepository;
import com.pixelocura.bitscafe.service.AdminCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminCountryServiceImpl implements AdminCountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Page<Country> paginate(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    @Override
    public Country create(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country findByIsoCode(String isoCode) {
        return countryRepository.findById(isoCode)
                .orElseThrow(() -> new RuntimeException("Country not found with ISO code: " + isoCode));
    }

    @Override
    public Country update(String isoCode, Country updatedCountry) {
        Country existingCountry = findByIsoCode(isoCode);

        if (updatedCountry.getName() != null) {
            existingCountry.setName(updatedCountry.getName());
        }
        if (updatedCountry.getFlagUrl() != null) {
            existingCountry.setFlagUrl(updatedCountry.getFlagUrl());
        }

        // Note: updateDate is handled by @PreUpdate
        return countryRepository.save(existingCountry);
    }

    @Override
    public void delete(String isoCode) {
        Country country = findByIsoCode(isoCode);
        countryRepository.delete(country);
    }
}
