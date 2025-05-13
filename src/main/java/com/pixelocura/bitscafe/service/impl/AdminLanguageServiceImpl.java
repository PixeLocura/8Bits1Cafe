package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Language;
import com.pixelocura.bitscafe.repository.LanguageRepository;
import com.pixelocura.bitscafe.service.AdminLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminLanguageServiceImpl implements AdminLanguageService {
    private final LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Page<Language> paginate(Pageable pageable) {
        return languageRepository.findAll(pageable);
    }

    @Override
    public Language create(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Language findByIsoCode(String isoCode) {
        return languageRepository.findById(isoCode)
                .orElseThrow(() -> new RuntimeException("Language not found with ISO code: " + isoCode));
    }

    @Override
    public Language update(String isoCode, Language updatedLanguage) {
        Language existingLanguage = findByIsoCode(isoCode);

        if (updatedLanguage.getName() != null) {
            existingLanguage.setName(updatedLanguage.getName());
        }

        return languageRepository.save(existingLanguage);
    }

    @Override
    public void delete(String isoCode) {
        Language language = findByIsoCode(isoCode);
        languageRepository.delete(language);
    }
}
