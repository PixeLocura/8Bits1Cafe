package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Category;
import com.pixelocura.bitscafe.repository.CategoryRepository;
import com.pixelocura.bitscafe.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> paginate(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category create(Category category) {
        //Validamos si la categoria ya existe para no crear de nuevo
        if (categoryRepository.existsByName(category.getName())){
            throw new RuntimeException("Ya existe una categoria con el nombre " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @Override
    public Category update(UUID id, Category updatedCategory) {
        Category existingCategory = findById(id);

        // Validar si el nuevo nombre ya existe (excepto para esta categoría)
        if (!existingCategory.getName().equals(updatedCategory.getName()) &&
                categoryRepository.existsByName(updatedCategory.getName())) {
            throw new RuntimeException("Ya existe otra categoría con el nombre: " + updatedCategory.getName());
        }

        // Actualizar los campos permitidos
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        // Puedes agregar más campos según tu entidad Category

        return categoryRepository.save(existingCategory);
    }
    @Override
    public void delete(UUID id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
