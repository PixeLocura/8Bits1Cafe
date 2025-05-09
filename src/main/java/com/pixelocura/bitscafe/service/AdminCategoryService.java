package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminCategoryService {
    List<Category> findAll();
    Page<Category> paginate(Pageable pageable);
    Category create(Category category);
    Category findById(UUID id);
    Category update(UUID id, Category updatedCategory);
    void delete(UUID id);
}
