package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.AuthResponseDTO;
import com.pixelocura.bitscafe.dto.LoginDTO;
import com.pixelocura.bitscafe.dto.UserProfileDTO;
import com.pixelocura.bitscafe.dto.UserRegistrationDTO;
import com.pixelocura.bitscafe.dto.ProfilePictureDTO;

import java.util.UUID;

public interface UserService {
    UserProfileDTO registerDeveloper(UserRegistrationDTO dto);
    UserProfileDTO registerAdmin(UserRegistrationDTO dto);
    UserProfileDTO updateUserProfile(UUID id, UserProfileDTO dto);
    UserProfileDTO getUserProfileById(UUID id);
    AuthResponseDTO login(LoginDTO loginDTO);
    UserProfileDTO updateProfilePicture(UUID id, ProfilePictureDTO dto);

}

