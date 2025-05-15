// AdminCategoryService.java
package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AdminCategoryService {
    List<CategoryDTO> findAll();
    Page<CategoryDTO> paginate(Pageable pageable);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO findById(UUID id);
    CategoryDTO update(UUID id, CategoryDTO categoryDTO);
    void delete(UUID id);
}
