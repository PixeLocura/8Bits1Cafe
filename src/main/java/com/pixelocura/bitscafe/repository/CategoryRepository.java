package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
