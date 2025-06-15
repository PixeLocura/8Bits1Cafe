package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.AuthResponseDTO;
import com.pixelocura.bitscafe.dto.LoginDTO;
import com.pixelocura.bitscafe.dto.UserProfileDTO;
import com.pixelocura.bitscafe.dto.UserRegistrationDTO;
import com.pixelocura.bitscafe.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register/developer")
    public ResponseEntity<UserProfileDTO> registerDeveloper(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfileDTO = userService.registerDeveloper(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserProfileDTO> registerAdmin(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfileDTO = userService.registerAdmin(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}


