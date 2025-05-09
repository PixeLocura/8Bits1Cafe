package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Country;
import com.pixelocura.bitscafe.service.AdminCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminCountryServiceImpl implements AdminCountryService {
    @Override
    public List<Country> findAll() {
        return List.of();
    }

    @Override
    public Page<Country> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Country create(Country country) {
        return null;
    }

    @Override
    public Country findById(UUID id) {
        return null;
    }

    @Override
    public Country update(UUID id, Country updatedCountry) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
