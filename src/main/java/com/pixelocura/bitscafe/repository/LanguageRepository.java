package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, String> {
    Optional<Language> findByIsoCode(String isoCode);
}
