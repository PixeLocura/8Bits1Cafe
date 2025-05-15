package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.UserDTO;
import com.pixelocura.bitscafe.mapper.UserMapper;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> paginate(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        // Check for duplicate username
        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "User already exists with username: " + userDTO.getUsername());
                });

        // Check for duplicate email
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "User already exists with email: " + userDTO.getEmail());
                });

        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with username: " + username));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO update(UUID id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with id: " + id));

        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Username already taken: " + userDTO.getUsername());
                    }
                });

        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Email already registered: " + userDTO.getEmail());
                    }
                });

        UUID currentDeveloperProfileId = existingUser.getDeveloperProfile() != null ?
                                         existingUser.getDeveloperProfile().getId() : null;

        userMapper.updateEntity(userDTO, existingUser);

        if (userDTO.getDeveloperProfileId() == null) {
            userRepository.removeDeveloperProfile(id);
        } else if (!userDTO.getDeveloperProfileId().equals(currentDeveloperProfileId)) {
            userRepository.updateDeveloperProfile(id, userDTO.getDeveloperProfileId());
        }

        User updatedUser = userRepository.findById(id).orElseThrow();
        return userMapper.toDTO(updatedUser);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
