package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.*;
import com.pixelocura.bitscafe.exception.ResourceNotFoundException;
import com.pixelocura.bitscafe.mapper.UserMapper;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.model.enums.ERole;
import com.pixelocura.bitscafe.repository.DeveloperRepository;
import com.pixelocura.bitscafe.repository.RoleRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.security.TokenProvider;
import com.pixelocura.bitscafe.security.UserPrincipal;
import com.pixelocura.bitscafe.service.AdminUserService;
import com.pixelocura.bitscafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AdminUserService adminUserService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final DeveloperRepository developerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; //Necesario para la autenticación
    private final TokenProvider tokenProvider; // Necesario para la creación de tokens JWT

    @Override
    public UserProfileDTO registerDeveloper(UserRegistrationDTO dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("Registration data cannot be null");
            }

            if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }

            // Encrypt password and prepare user data
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(encodedPassword);

            UserDTO userDTO = userMapper.fromRegistrationDTO(dto);
            if (userDTO == null) {
                throw new RuntimeException("Failed to map registration data to UserDTO");
            }

            userDTO.setRole(ERole.DEVELOPER);

            try {
                // Create user
                UserDTO createdUser = adminUserService.create(userDTO);
                if (createdUser == null || createdUser.getId() == null) {
                    throw new RuntimeException("User creation failed - no user data returned");
                }

                // Retrieve complete User from database
                User userEntity = userRepository.findById(createdUser.getId())
                        .orElseThrow(() -> new RuntimeException(String.format(
                            "Failed to retrieve created user with ID %s", createdUser.getId())));

                return userMapper.toUserProfileDTO(userEntity);
            } catch (Exception e) {
                throw new RuntimeException("Error during user creation process: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        }
    }

    @Override
    public UserProfileDTO registerAdmin(UserRegistrationDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserDTO userDTO = userMapper.fromRegistrationDTO(dto);
        userDTO.setRole(ERole.ADMIN);

        // Crear usuario
        UserDTO createdUser = adminUserService.create(userDTO);

        // Recuperar el User completo desde la base de datos (con Role bien cargado)
        User userEntity = userRepository.findById(createdUser.getId())
                .orElseThrow(() -> new RuntimeException("Usuario creado no encontrado"));

        return userMapper.toUserProfileDTO(userEntity);
    }


    @Override
    public UserProfileDTO updateUserProfile(UUID id, UserProfileDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        boolean nameExists = developerRepository.existsByNameAndUser_IdNot(dto.getName(), id);
        if (nameExists) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre");
        }

        if (user.getDeveloperProfile() != null) {
            user.getDeveloperProfile().setName(dto.getName());
        }else {
            user.setName(dto.getName());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Override
    public UserProfileDTO getUserProfileById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return userMapper.toUserProfileDTO(user);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        try {
            if (loginDTO == null) {
                throw new IllegalArgumentException("Login data cannot be null");
            }

            if (loginDTO.getEmail() == null || loginDTO.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }

            if (loginDTO.getPassword() == null || loginDTO.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }

            // Authenticate user using AuthenticationManager
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
                );
            } catch (Exception e) {
                throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
            }

            // Get authenticated user info
            if (!(authentication.getPrincipal() instanceof UserPrincipal)) {
                throw new RuntimeException("Unexpected authentication principal type: " +
                    authentication.getPrincipal().getClass().getName());
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userPrincipal.getUser();

            if (user == null) {
                throw new RuntimeException("No user data found in authentication principal");
            }

            // Generate JWT token
            String token;
            try {
                token = tokenProvider.createAccessToken(authentication);
                if (token == null || token.trim().isEmpty()) {
                    throw new RuntimeException("Token generation failed - empty token returned");
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate access token: " + e.getMessage(), e);
            }

            // Generate authentication response
            try {
                return userMapper.toAuthResponseDTO(user, token);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate auth response: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Login process failed: " + e.getMessage(), e);
        }
    }

}
