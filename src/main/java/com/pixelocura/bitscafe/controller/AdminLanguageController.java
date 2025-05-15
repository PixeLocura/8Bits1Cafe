package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.entity.Language;
import com.pixelocura.bitscafe.service.AdminLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/languages")
public class AdminLanguageController {

    private final AdminLanguageService adminLanguageService;

    /** Listar todos los idiomas */
    @GetMapping
    public ResponseEntity<List<Language>> listAll() {
        List<Language> languages = adminLanguageService.findAll();
        return ResponseEntity.ok(languages);
    }

    /** Listado paginado de idiomas */
    @GetMapping("/page")
    public ResponseEntity<Page<Language>> paginate(@PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<Language> page = adminLanguageService.paginate(pageable);
        return ResponseEntity.ok(page);
    }

    /** Crear un nuevo idioma */
    @PostMapping
    public ResponseEntity<Language> create(@RequestBody Language language) {
        Language created = adminLanguageService.create(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Obtener un idioma por su código ISO */
    @GetMapping("/{isoCode}")
    public ResponseEntity<Language> getByIsoCode(@PathVariable String isoCode) {
        Language language = adminLanguageService.findByIsoCode(isoCode);
        return ResponseEntity.ok(language);
    }

    /** Actualizar un idioma existente */
    @PutMapping("/{isoCode}")
    public ResponseEntity<Language> update(
            @PathVariable String isoCode,
            @RequestBody Language updatedLanguage
    ) {
        Language language = adminLanguageService.update(isoCode, updatedLanguage);
        return ResponseEntity.ok(language);
    }

    /** Eliminar un idioma */
    @DeleteMapping("/{isoCode}")
    public ResponseEntity<Void> delete(@PathVariable String isoCode) {
        adminLanguageService.delete(isoCode);
        return ResponseEntity.noContent().build();
    }
}
