package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.UserProfileDTO;
import com.pixelocura.bitscafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserService userService;

    // Actualizar el perfil
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateProfile(@PathVariable UUID id, @RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updatedProfile = userService.updateUserProfile(id, userProfileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // Obtener el perfil
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable UUID id) {
        UserProfileDTO profile = userService.getUserProfileById(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
