package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.enums.Category;
import com.pixelocura.bitscafe.service.CategoryEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryEnumController {

    private final CategoryEnumService categoryEnumService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryEnumService.getAllCategories());
    }

    @GetMapping("/names")
    public ResponseEntity<Map<String, String>> getCategoriesWithDisplayNames() {
        return ResponseEntity.ok(categoryEnumService.getCategoriesWithDisplayNames());
    }
}
