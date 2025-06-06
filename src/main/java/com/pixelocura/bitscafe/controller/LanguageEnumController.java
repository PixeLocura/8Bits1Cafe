package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.enums.Language;
import com.pixelocura.bitscafe.service.LanguageEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/languages")
public class LanguageEnumController {
    private final LanguageEnumService languageEnumService;

    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageEnumService.getAllLanguages());
    }

    @GetMapping("/names")
    public ResponseEntity<Map<String, Map<String, String>>> getLanguagesWithNames() {
        return ResponseEntity.ok(languageEnumService.getLanguagesWithNames());
    }

    @GetMapping("/isocode/{code}")
    public ResponseEntity<Map<String, String>> getLanguageByIsoCode(@PathVariable String code) {
        Optional<Language> language = languageEnumService.getLanguageByIsoCode(code);
        return language.map(lang -> ResponseEntity.ok(Map.of(
                "isoCode", lang.name(),
                "name", lang.getName(),
                "localName", lang.getLocalName()
        ))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
