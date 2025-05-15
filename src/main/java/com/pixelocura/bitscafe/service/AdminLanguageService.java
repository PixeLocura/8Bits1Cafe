package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.LanguageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminLanguageService {
    List<LanguageDTO> findAll();
    Page<LanguageDTO> paginate(Pageable pageable);
    LanguageDTO create(LanguageDTO language);
    LanguageDTO findByIsoCode(String isoCode);
    LanguageDTO update(String isoCode, LanguageDTO updatedLanguage);
    void delete(String isoCode);
}
