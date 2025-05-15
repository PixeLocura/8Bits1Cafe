// CategoryMapper.java
package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.CategoryDTO;
import com.pixelocura.bitscafe.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public CategoryDTO toDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public void updateEntity(CategoryDTO categoryDTO, Category category) {
        modelMapper.map(categoryDTO, category);
    }
}