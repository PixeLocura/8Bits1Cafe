package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LanguageRepository extends JpaRepository<Language, UUID> {
}
