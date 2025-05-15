package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.UserDTO;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.model.entity.User;
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
        return dto;
    }

    public User toEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        // Temporarily use plain password as hash
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPasswordHash(userDTO.getPassword());
        }

        if (userDTO.getDeveloperProfileId() != null) {
            Developer developer = developerRepository.findById(userDTO.getDeveloperProfileId())
                .orElseThrow(() -> new RuntimeException("Developer not found with id: " + userDTO.getDeveloperProfileId()));
            user.setDeveloperProfile(developer);
        }
        return user;
    }

    public void updateEntity(UserDTO userDTO, User user) {
        // Store values that should not be overwritten by ModelMapper
        String existingPasswordHash = user.getPasswordHash();

        user.setDeveloperProfile(null);

        // Configure ModelMapper to skip the developerProfile field
        ModelMapper tmpMapper = new ModelMapper();
        tmpMapper.getConfiguration().setSkipNullEnabled(true);
        tmpMapper.createTypeMap(UserDTO.class, User.class)
            .addMappings(mapper -> mapper.skip(User::setDeveloperProfile));

        // Map userDTO to user, skipping developer profile
        tmpMapper.map(userDTO, user);

        // Handle password - temporarily use plain password as hash
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPasswordHash(userDTO.getPassword());
        } else {
            user.setPasswordHash(existingPasswordHash);
        }
    }
}
