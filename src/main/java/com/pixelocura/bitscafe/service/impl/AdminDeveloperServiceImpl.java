package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.service.AdminDeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class AdminDeveloperServiceImpl implements AdminDeveloperService {
    @Override
    public List<Developer> findAll() {
        return List.of();
    }

    @Override
    public Page<Developer> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Developer create(Developer developer) {
        return null;
    }

    @Override
    public Developer findById(UUID id) {
        return null;
    }

    @Override
    public Developer update(UUID id, Developer updatedDeveloper) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
