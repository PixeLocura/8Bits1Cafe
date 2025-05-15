package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.PlatformDTO;
import com.pixelocura.bitscafe.service.AdminPlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/platforms")
public class AdminPlatformController {
    private final AdminPlatformService adminPlatformService;

    @GetMapping
    public ResponseEntity<List<PlatformDTO>> list() {
        List<PlatformDTO> platforms = adminPlatformService.findAll();
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PlatformDTO>> paginate(@PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<PlatformDTO> platforms = adminPlatformService.paginate(pageable);
        return ResponseEntity.ok(platforms);
    }

    @PostMapping
    public ResponseEntity<PlatformDTO> create(@Valid @RequestBody PlatformDTO platformDTO) {
        PlatformDTO createdPlatform = adminPlatformService.create(platformDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlatform);
    }

    @GetMapping("/{id}")
public ResponseEntity<PlatformDTO> getById(@PathVariable UUID id) {
        PlatformDTO platform = adminPlatformService.findById(id);
        return ResponseEntity.ok(platform);
    }

    @PutMapping("/{id}")
public ResponseEntity<PlatformDTO> update(@PathVariable UUID id, @Valid @RequestBody PlatformDTO platformDTO) {
        PlatformDTO updatedPlatform = adminPlatformService.update(id, platformDTO);
        return ResponseEntity.ok(updatedPlatform);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminPlatformService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
