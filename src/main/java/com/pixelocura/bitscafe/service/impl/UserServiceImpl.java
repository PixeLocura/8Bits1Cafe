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
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserDTO userDTO = userMapper.fromRegistrationDTO(dto);
        userDTO.setRole(ERole.DEVELOPER);

        // Crear usuario
        UserDTO createdUser = adminUserService.create(userDTO);

        // Recuperar el User completo desde la base de datos (con Role y Developer cargados)
        User userEntity = userRepository.findById(createdUser.getId())
                .orElseThrow(() -> new RuntimeException("Usuario creado no encontrado"));

        return userMapper.toUserProfileDTO(userEntity);
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

        boolean nameExists = developerRepository.existsByNameAndUserIdNot(dto.getName(), id);
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
        // Autenticar al usuario utilizando AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
                );
        // Una vez autenticado, el objeto autentication contiene la informacion del usuario autenticado
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        User user = userPrincipal.getUser();
        // Verificar si es un administrador
        boolean isAdmin = user.getRole().getName().equals(ERole.ADMIN);
        //String token = "abc123";
        // Generar el token JWT usando el TokenProvider
        String token = tokenProvider.createAccessToken(authentication);
        // Generar la respuesta de autenticación, con el rol correspondiente
        AuthResponseDTO responseDTO = userMapper.toAuthResponseDTO(user, token);
        // Retornar la respuesta
        return responseDTO;
    }

}
