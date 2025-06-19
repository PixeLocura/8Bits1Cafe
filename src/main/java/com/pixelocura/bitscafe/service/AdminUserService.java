package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AdminUserService {
    List<UserDTO> findAll();
    Page<UserDTO> paginate(Pageable pageable);
    UserDTO create(UserDTO userDTO);
    UserDTO findById(UUID id);
    UserDTO findByUsername(String username);
    UserDTO findByEmail(String email);
    UserDTO update(UUID id, UserDTO userDTO);
    void delete(UUID id);
}
