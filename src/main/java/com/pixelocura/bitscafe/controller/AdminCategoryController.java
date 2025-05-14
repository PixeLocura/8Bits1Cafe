package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.entity.Category;
import com.pixelocura.bitscafe.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(adminCategoryService.findAll());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Category>> getAllCategoriesPaginated(Pageable pageable) {
        return ResponseEntity.ok(adminCategoryService.paginate(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(adminCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(adminCategoryService.create(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable UUID id,
            @RequestBody Category category) {
        return ResponseEntity.ok(adminCategoryService.update(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        adminCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}