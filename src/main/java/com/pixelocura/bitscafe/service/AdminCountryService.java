package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminCountryService {
    List<Country> findAll();
    Page<Country> paginate(Pageable pageable);
    Country create(Country country);
    Country findByIsoCode(String isoCode);
    Country update(String isoCode, Country updatedCountry);
    void delete(String isoCode);
}
