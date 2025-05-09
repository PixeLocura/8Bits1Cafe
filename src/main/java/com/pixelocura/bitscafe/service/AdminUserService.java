package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminUserService {
    List<User> findAll();
    Page<User> paginate(Pageable pageable);
    User create(User user);
    User findById(UUID id);
    User update(UUID id, User updatedUser);
    void delete(UUID id);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
