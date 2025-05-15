package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminCountryService {
    List<CountryDTO> findAll();
    Page<CountryDTO> paginate(Pageable pageable);
    CountryDTO create(CountryDTO countryDTO);
    CountryDTO findByIsoCode(String isoCode);
    CountryDTO update(String isoCode, CountryDTO countryDTO);
    void delete(String isoCode);
}
