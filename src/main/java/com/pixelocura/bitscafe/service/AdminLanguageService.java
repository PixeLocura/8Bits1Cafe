package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminLanguageService {
    List<Language> findAll();
    Page<Language> paginate(Pageable pageable);
    Language create(Language language);
    Language findByIsoCode(String isoCode);
    Language update(String isoCode, Language updatedLanguage);
    void delete(String isoCode);
}
