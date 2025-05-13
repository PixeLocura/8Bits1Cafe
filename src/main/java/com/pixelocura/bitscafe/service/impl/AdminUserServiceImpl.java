package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> paginate(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User create(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User update(UUID id, User updatedUser) {
        User existingUser = findById(id);

        // Check username uniqueness only if username is being updated
        if (updatedUser.getUsername() != null) {
            if (!existingUser.getUsername().equals(updatedUser.getUsername())
                && existsByUsername(updatedUser.getUsername())) {
                throw new RuntimeException("Username already exists: " + updatedUser.getUsername());
            }
            existingUser.setUsername(updatedUser.getUsername());
        }

        // Check email uniqueness only if email is being updated
        if (updatedUser.getEmail() != null) {
            if (!existingUser.getEmail().equals(updatedUser.getEmail())
                && existsByEmail(updatedUser.getEmail())) {
                throw new RuntimeException("Email already exists: " + updatedUser.getEmail());
            }
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getLastname() != null) {
            existingUser.setLastname(updatedUser.getLastname());
        }
        if (updatedUser.getPasswordHash() != null) {
            existingUser.setPasswordHash(updatedUser.getPasswordHash());
        }
        if (updatedUser.getCountry() != null) {
            existingUser.setCountry(updatedUser.getCountry());
        }
        if (updatedUser.getDeveloperProfile() != null) {
            existingUser.setDeveloperProfile(updatedUser.getDeveloperProfile());
        }

        // Note: updateDate is handled by @PreUpdate
        return userRepository.save(existingUser);
    }

    @Override
    public void delete(UUID id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
