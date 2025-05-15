package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.service.AdminDeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/developers")
public class AdminDeveloperController {
    private final AdminDeveloperService adminDeveloperService;

    @GetMapping
    public ResponseEntity<List<DeveloperDTO>> list() {
        List<DeveloperDTO> developers = adminDeveloperService.findAll();
        return ResponseEntity.ok(developers);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DeveloperDTO>> paginate(@PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<DeveloperDTO> developers = adminDeveloperService.paginate(pageable);
        return ResponseEntity.ok(developers);
    }

    @PostMapping
    public ResponseEntity<DeveloperDTO> create(@Valid @RequestBody DeveloperDTO developerDTO) {
        DeveloperDTO createdDeveloper = adminDeveloperService.create(developerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeveloper);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDTO> get(@PathVariable UUID id) {
        DeveloperDTO developer = adminDeveloperService.findById(id);
        return ResponseEntity.ok(developer);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<DeveloperDTO> getByName(@PathVariable String name) {
        DeveloperDTO developer = adminDeveloperService.findByName(name);
        return ResponseEntity.ok(developer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDTO> update(@PathVariable UUID id, @Valid @RequestBody DeveloperDTO developerDTO) {
        DeveloperDTO updatedDeveloper = adminDeveloperService.update(id, developerDTO);
        return ResponseEntity.ok(updatedDeveloper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminDeveloperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
