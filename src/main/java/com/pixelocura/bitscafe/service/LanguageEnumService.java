package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.enums.Language;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LanguageEnumService {
    public List<Language> getAllLanguages() {
        return Arrays.asList(Language.values());
    }

    public Map<String, Map<String, String>> getLanguagesWithNames() {
        return Arrays.stream(Language.values())
                .collect(Collectors.toMap(
                        Language::name,
                        language -> Map.of(
                                "name", language.getName(),
                                "localName", language.getLocalName())));
    }

    public Optional<Language> getLanguageByIsoCode(String isoCode) {
        return Language.fromIsoCode(isoCode);
    }

    public Optional<Language> getLanguageByName(String name) {
        return Language.fromName(name);
    }

    public Optional<Language> getLanguageByLocalName(String localName) {
        return Language.fromLocalName(localName);
    }
}
