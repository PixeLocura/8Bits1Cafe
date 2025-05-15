package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.CategoryDTO;
import com.pixelocura.bitscafe.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(adminCategoryService.findAll());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<CategoryDTO>> getAllPaginated(Pageable pageable) {
        return ResponseEntity.ok(adminCategoryService.paginate(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(adminCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(adminCategoryService.create(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable UUID id,
            @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(adminCategoryService.update(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}