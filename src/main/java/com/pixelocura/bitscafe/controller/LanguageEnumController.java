package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.enums.Language;
import com.pixelocura.bitscafe.service.LanguageEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Language> getLanguageByIsoCode(@PathVariable String code) {
        Language language = languageEnumService.getLanguageByIsoCode(code);
        return language != null ? ResponseEntity.ok(language) : ResponseEntity.notFound().build();
    }
}
