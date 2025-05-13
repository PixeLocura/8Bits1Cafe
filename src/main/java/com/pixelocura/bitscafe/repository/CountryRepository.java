package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> findByIsoCode(String isoCode);
}
