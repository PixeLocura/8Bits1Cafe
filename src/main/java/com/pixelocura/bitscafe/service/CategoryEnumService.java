package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.enums.Category;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryEnumService {
    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }

    public Map<String, String> getCategoriesWithDisplayNames() {
        return Arrays.stream(Category.values())
                .collect(Collectors.toMap(
                        Category::name,
                        Category::getDisplayName));
    }

    public Category getCategoryByName(String name) {
        try {
            return Category.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Category getCategoryByDisplayName(String displayName) {
        return Arrays.stream(Category.values())
                .filter(category -> category.getDisplayName().equalsIgnoreCase(displayName))
                .findFirst()
                .orElse(null);
    }
}
