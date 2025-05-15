package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.LanguageDTO;
import com.pixelocura.bitscafe.exception.BadRequestException;
import com.pixelocura.bitscafe.mapper.LanguageMapper;
import com.pixelocura.bitscafe.model.entity.Language;
import com.pixelocura.bitscafe.repository.LanguageRepository;
import com.pixelocura.bitscafe.service.AdminLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminLanguageServiceImpl implements AdminLanguageService {
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LanguageDTO> findAll() {
        return languageRepository.findAll().stream()
                .map(languageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> paginate(Pageable pageable) {
        return languageRepository.findAll(pageable)
                .map(languageMapper::toDTO);
    }

    @Override
    @Transactional
    public LanguageDTO create(LanguageDTO languageDTO) {
        Language language = languageMapper.toEntity(languageDTO);
        Language savedLanguage = languageRepository.save(language);
        return languageMapper.toDTO(savedLanguage);
    }

    @Override
    @Transactional(readOnly = true)
    public LanguageDTO findByIsoCode(String isoCode) {
        Language language = languageRepository.findByIsoCode(isoCode)
                .orElseThrow(()-> new BadRequestException("Language not found with ISO code: " + isoCode));
        return languageMapper.toDTO(language);
    }

    @Override
    @Transactional
    public LanguageDTO update(String isoCode, LanguageDTO languageDTO) {
        Language languageEntity = languageRepository.findByIsoCode(isoCode)
                .orElseThrow(()-> new BadRequestException("Language not found with ISO code: " + isoCode));
        if(languageDTO.getName() != null){
            languageEntity.setName(languageDTO.getName());
        }
        Language updatedLanguage = languageRepository.save(languageEntity);
        return languageMapper.toDTO(updatedLanguage);
    }

    @Override
    @Transactional
    public void delete(String isoCode) {
        Language languageToDelete = languageRepository.findByIsoCode(isoCode)
                .orElseThrow(()-> new BadRequestException("Language not found with ISO code: " + isoCode));

        languageRepository.delete(languageToDelete);
    }
}
