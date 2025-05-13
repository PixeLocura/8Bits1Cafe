package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, String> {
}
