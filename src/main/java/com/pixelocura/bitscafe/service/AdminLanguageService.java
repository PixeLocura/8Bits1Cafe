package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminLanguageService {
    List<Language> findAll();
    Page<Language> paginate(Pageable pageable);
    Language create(Language language);
    Language findById(UUID id);
    Language update(UUID id, Language updatedLanguage);
    void delete(UUID id);
}
