package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.UserDTO;
import com.pixelocura.bitscafe.mapper.UserMapper;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AdminUserServiceImpl userService;

    private final UUID userId = UUID.randomUUID();
    private final String username = "testUser";
    private final String email = "test@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUserWithValues(UUID id, String username, String email) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

    private UserDTO createUserDTOWithValues(UUID id, String username, String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        return userDTO;
    }

    @Test
    @DisplayName("CP01 - Listar usuarios con datos")
    void findAll_withData_returnsList() {
        // Arrange
        User user1 = createUserWithValues(userId, username, email);
        UUID user2Id = UUID.randomUUID();
        User user2 = createUserWithValues(user2Id, "user2", "user2@example.com");

        UserDTO dto1 = createUserDTOWithValues(userId, username, email);
        UserDTO dto2 = createUserDTOWithValues(user2Id, "user2", "user2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.toDTO(user1)).thenReturn(dto1);
        when(userMapper.toDTO(user2)).thenReturn(dto2);

        // Act
        List<UserDTO> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(username, result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    @DisplayName("CP02 - Listar usuarios sin datos")
    void findAll_noData_returnsEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<UserDTO> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("CP03 - Listar usuarios con paginación")
    void paginate_withData_returnsPageOfUsers() {
        // Arrange
        User user = createUserWithValues(userId, username, email);
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        Page<UserDTO> result = userService.paginate(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userDTO.getUsername(), result.getContent().getFirst().getUsername());
    }

    @Test
    @DisplayName("CP04 - Crear usuario con datos válidos")
    void create_validData_returnsCreatedUser() {
        // Arrange
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);
        User user = createUserWithValues(userId, username, email);

        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.create(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userDTO.getUsername(), result.getUsername());
        assertEquals(userDTO.getEmail(), result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("CP05 - Crear usuario con nombre de usuario duplicado")
    void create_duplicateUsername_throwsException() {
        // Arrange
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);
        User existingUser = createUserWithValues(userId, username, email);

        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.create(userDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User already exists with username"));
    }

    @Test
    @DisplayName("CP06 - Crear usuario con email duplicado")
    void create_duplicateEmail_throwsException() {
        // Arrange
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);
        User existingUser = createUserWithValues(userId, username, email);

        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.create(userDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User already exists with email"));
    }

    @Test
    @DisplayName("CP07 - Buscar usuario por ID existente")
    void findById_existingId_returnsUser() {
        // Arrange
        User user = createUserWithValues(userId, username, email);
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.findById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(username, result.getUsername());
    }

    @Test
    @DisplayName("CP08 - Buscar usuario por ID inexistente")
    void findById_nonExistingId_throwsException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.findById(userId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("CP09 - Buscar usuario por nombre de usuario existente")
    void findByUsername_existingUsername_returnsUser() {
        // Arrange
        User user = createUserWithValues(userId, username, email);
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    @DisplayName("CP10 - Buscar usuario por nombre de usuario inexistente")
    void findByUsername_nonExistingUsername_throwsException() {
        // Arrange
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.findByUsername(username));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("CP11 - Buscar usuario por email existente")
    void findByEmail_existingEmail_returnsUser() {
        // Arrange
        User user = createUserWithValues(userId, username, email);
        UserDTO userDTO = createUserDTOWithValues(userId, username, email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    @DisplayName("CP12 - Buscar usuario por email inexistente")
    void findByEmail_nonExistingEmail_throwsException() {
        // Arrange
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.findByEmail(email));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("CP13 - Actualizar usuario exitosamente")
    void update_validData_returnsUpdatedUser() {
        // Arrange
        User existingUser = createUserWithValues(userId, username, email);
        UserDTO updateDTO = createUserDTOWithValues(userId, username, email);
        updateDTO.setUsername("newUsername");
        updateDTO.setEmail("new@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(userMapper.toDTO(existingUser)).thenReturn(updateDTO);

        // Act
        UserDTO result = userService.update(userId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("newUsername", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    @DisplayName("CP14 - Actualizar usuario con nombre de usuario duplicado")
    void update_duplicateUsername_throwsException() {
        // Arrange
        User existingUser = createUserWithValues(userId, username, email);
        UUID anotherId = UUID.randomUUID();
        User anotherUser = createUserWithValues(anotherId, "newUsername", "another@example.com");
        UserDTO updateDTO = createUserDTOWithValues(userId, username, email);
        updateDTO.setUsername("newUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.of(anotherUser));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.update(userId, updateDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Username already taken"));
    }

    @Test
    @DisplayName("CP15 - Actualizar usuario con email duplicado")
    void update_duplicateEmail_throwsException() {
        // Arrange
        User existingUser = createUserWithValues(userId, username, email);
        UUID anotherId = UUID.randomUUID();
        User anotherUser = createUserWithValues(anotherId, "anotherUser", "new@example.com");
        UserDTO updateDTO = createUserDTOWithValues(userId, username, email);
        updateDTO.setEmail("new@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.of(anotherUser));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.update(userId, updateDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Email already registered"));
    }

    @Test
    @DisplayName("CP16 - Actualizar usuario con perfil de desarrollador")
    void update_withDeveloperProfile_updatesProfile() {
        // Arrange
        User existingUser = createUserWithValues(userId, username, email);
        UserDTO updateDTO = createUserDTOWithValues(userId, username, email);
        UUID newDeveloperProfileId = UUID.randomUUID();
        updateDTO.setDeveloperProfileId(newDeveloperProfileId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userMapper.toDTO(existingUser)).thenReturn(updateDTO);

        // Act
        UserDTO result = userService.update(userId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(newDeveloperProfileId, result.getDeveloperProfileId());
        verify(userRepository).updateDeveloperProfile(userId, newDeveloperProfileId);
    }

    @Test
    @DisplayName("CP17 - Eliminar usuario existente")
    void delete_existingUser_deletesSuccessfully() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository).deleteById(userId);
    }

    @Test
    @DisplayName("CP18 - Eliminar usuario inexistente")
    void delete_nonExistingUser_throwsException() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> userService.delete(userId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
