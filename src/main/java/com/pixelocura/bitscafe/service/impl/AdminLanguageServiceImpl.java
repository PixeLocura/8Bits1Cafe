package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Language;
import com.pixelocura.bitscafe.service.AdminLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminLanguageServiceImpl implements AdminLanguageService {
    @Override
    public List<Language> findAll() {
        return List.of();
    }

    @Override
    public Page<Language> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Language create(Language language) {
        return null;
    }

    @Override
    public Language findById(UUID id) {
        return null;
    }

    @Override
    public Language update(UUID id, Language updatedLanguage) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
