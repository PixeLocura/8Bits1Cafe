package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminDeveloperService {
    List<Developer> findAll();
    Page<Developer> paginate(Pageable pageable);
    Developer create(Developer developer);
    Developer findById(UUID id);
    Developer update(UUID id, Developer updatedDeveloper);
    void delete(UUID id);
}
