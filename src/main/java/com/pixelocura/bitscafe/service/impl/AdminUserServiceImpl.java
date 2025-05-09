package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Page<User> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User findById(UUID id) {
        return null;
    }

    @Override
    public User update(UUID id, User updatedUser) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
