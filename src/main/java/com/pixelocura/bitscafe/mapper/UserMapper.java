package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.*;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.model.enums.Country;
import com.pixelocura.bitscafe.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final DeveloperRepository developerRepository;

    public UserDTO toDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setPassword(null);

        if (user.getDeveloperProfile() != null) {
            dto.setDeveloperProfileId(user.getDeveloperProfile().getId());
        } else {
            dto.setDeveloperProfileId(null);
        }

        // Asegúrate de incluir el campo:
        dto.setProfilePictureUrl(user.getProfilePictureUrl());

        dto.setName(user.getName());

        if (user.getRole() != null) {
            dto.setRole(user.getRole().getName());
        }
        return dto;
    }



    public User toEntity(UserDTO userDTO) {
        User user = new User();

        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setUsername(userDTO.getUsername());
        user.setCountry(userDTO.getCountry());

        // Nuevo: foto
        user.setProfilePictureUrl(userDTO.getProfilePictureUrl());

        if (userDTO.getDeveloperProfileId() != null) {
            Developer developer = developerRepository.findById(userDTO.getDeveloperProfileId())
                    .orElseThrow(() -> new RuntimeException("Developer not found with id: " + userDTO.getDeveloperProfileId()));
            user.setDeveloperProfile(developer);
        }

        return user;
    }


    public void updateEntity(UserDTO userDTO, User user) {
        String existingPasswordHash = user.getPasswordHash();
        user.setDeveloperProfile(null);

        var typeMap = modelMapper.getTypeMap(UserDTO.class, User.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(UserDTO.class, User.class);
            typeMap.addMappings(mapper -> mapper.skip(User::setDeveloperProfile));
        }
        typeMap.setPropertyCondition(context -> context.getSource() != null);

        modelMapper.map(userDTO, user);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPasswordHash(userDTO.getPassword());
        } else {
            user.setPasswordHash(existingPasswordHash);
        }

        // Nuevo: foto
        if (userDTO.getProfilePictureUrl() != null) {
            user.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        }
    }


    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());

        dto.setProfilePictureUrl(user.getProfilePictureUrl()); // 🚩 Aquí

        if (user != null && user.getRoleName() != null) {
            dto.setRole(user.getRoleName());
        }

        if (user.getDeveloperProfile() != null) {
            dto.setName(user.getDeveloperProfile().getName());
        } else {
            dto.setName(user.getName());
        }

        return dto;
    }



    public UserDTO fromRegistrationDTO(UserRegistrationDTO registrationDTO) {
        UserDTO dto = new UserDTO();
        dto.setUsername(registrationDTO.getUsername());
        dto.setEmail(registrationDTO.getEmail());
        dto.setPassword(registrationDTO.getPassword());
        dto.setName(registrationDTO.getName());
        dto.setLastname(registrationDTO.getLastname());

        // Convertir de String a Enum Country
        Country country = Country.fromIsoCode(registrationDTO.getCountryIso())
                .orElseThrow(() -> new IllegalArgumentException("País no válido: " + registrationDTO.getCountryIso()));
        dto.setCountry(country);

        return dto;
    }

    // ------- Para Uso de AUTENTICACION/AUTORIZACION -------
// Convertir de LoginDTO a User (cuando procesas el login)
    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }
// Convertir de User a AuthResponseDTO para la respuesta de autenticación
    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token); // Asignar el token
        // Si es developer, asignar sus datos
        if (user.getDeveloperProfile() != null) {
            authResponseDTO.setName(user.getDeveloperProfile().getName());
        }
 /*
 // Si es otro: autor, asignar los datos de autor
 else if (user.getAuthor() != null) {
 authResponseDTO.setFirstName(user.getAuthor().getFirstName());
 authResponseDTO.setLastName(user.getAuthor().getLastName());
 }
 */
        // Para cualquier usuario que no sea developer ni autor (ej.Admin)
        else {
            authResponseDTO.setName("Admin");
        }
        // Asignar el rol del usuario
        authResponseDTO.setRole(user.getRole().getName().toString());

        return authResponseDTO;
    }
}
