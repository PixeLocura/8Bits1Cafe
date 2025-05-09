package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminCountryService {
    List<Country> findAll();
    Page<Country> paginate(Pageable pageable);
    Country create(Country country);
    Country findById(UUID id);
    Country update(UUID id, Country updatedCountry);
    void delete(UUID id);
}
