package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByIsoCode(String isoCode);
}
