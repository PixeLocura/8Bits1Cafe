package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Category;
import com.pixelocura.bitscafe.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Page<Category> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public Category findById(UUID id) {
        return null;
    }

    @Override
    public Category update(UUID id, Category updatedCategory) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
