package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.mapper.DeveloperMapper;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.repository.DeveloperRepository;
import com.pixelocura.bitscafe.service.impl.AdminDeveloperServiceImpl;
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

public class DeveloperServiceUnitTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperMapper developerMapper;

    @InjectMocks
    private AdminDeveloperServiceImpl developerService;

    private final UUID developerId = UUID.randomUUID();
    private final String developerName = "Test Developer";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Developer createDeveloperWithValues(UUID id, String name) {
        Developer developer = new Developer();
        developer.setId(id);
        developer.setName(name);
        return developer;
    }

    private DeveloperDTO createDeveloperDTOWithValues(UUID id, String name) {
        DeveloperDTO developerDTO = new DeveloperDTO();
        developerDTO.setId(id);
        developerDTO.setName(name);
        return developerDTO;
    }

    @Test
    @DisplayName("CP01 - Listar desarrolladores con datos")
    void findAll_withData_returnsList() {
        // Arrange
        Developer dev1 = createDeveloperWithValues(developerId, developerName);
        UUID dev2Id = UUID.randomUUID();
        Developer dev2 = createDeveloperWithValues(dev2Id, "Another Developer");

        DeveloperDTO dto1 = createDeveloperDTOWithValues(developerId, developerName);
        DeveloperDTO dto2 = createDeveloperDTOWithValues(dev2Id, "Another Developer");

        when(developerRepository.findAll()).thenReturn(Arrays.asList(dev1, dev2));
        when(developerMapper.toDTO(dev1)).thenReturn(dto1);
        when(developerMapper.toDTO(dev2)).thenReturn(dto2);

        // Act
        List<DeveloperDTO> result = developerService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(developerName, result.get(0).getName());
        assertEquals("Another Developer", result.get(1).getName());
    }

    @Test
    @DisplayName("CP02 - Listar desarrolladores sin datos")
    void findAll_noData_returnsEmptyList() {
        // Arrange
        when(developerRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<DeveloperDTO> result = developerService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("CP03 - Listar desarrolladores con paginación")
    void paginate_withData_returnsPageOfDevelopers() {
        // Arrange
        Developer developer = createDeveloperWithValues(developerId, developerName);
        DeveloperDTO developerDTO = createDeveloperDTOWithValues(developerId, developerName);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Developer> developerPage = new PageImpl<>(Collections.singletonList(developer));

        when(developerRepository.findAll(pageable)).thenReturn(developerPage);
        when(developerMapper.toDTO(developer)).thenReturn(developerDTO);

        // Act
        Page<DeveloperDTO> result = developerService.paginate(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(developerName, result.getContent().getFirst().getName());
    }

    @Test
    @DisplayName("CP04 - Crear desarrollador con datos válidos")
    void create_validData_returnsCreatedDeveloper() {
        // Arrange
        DeveloperDTO developerDTO = createDeveloperDTOWithValues(developerId, developerName);
        Developer developer = createDeveloperWithValues(developerId, developerName);

        when(developerRepository.findByName(developerDTO.getName())).thenReturn(Optional.empty());
        when(developerMapper.toEntity(developerDTO)).thenReturn(developer);
        when(developerRepository.save(developer)).thenReturn(developer);
        when(developerMapper.toDTO(developer)).thenReturn(developerDTO);

        // Act
        DeveloperDTO result = developerService.create(developerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(developerDTO.getName(), result.getName());
        verify(developerRepository).save(developer);
    }

    @Test
    @DisplayName("CP05 - Crear desarrollador con nombre duplicado")
    void create_duplicateName_throwsException() {
        // Arrange
        DeveloperDTO developerDTO = createDeveloperDTOWithValues(developerId, developerName);
        Developer existingDeveloper = createDeveloperWithValues(developerId, developerName);

        when(developerRepository.findByName(developerDTO.getName())).thenReturn(Optional.of(existingDeveloper));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> developerService.create(developerDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Developer already exists with name"));
    }

    @Test
    @DisplayName("CP06 - Buscar desarrollador por ID existente")
    void findById_existingId_returnsDeveloper() {
        // Arrange
        Developer developer = createDeveloperWithValues(developerId, developerName);
        DeveloperDTO developerDTO = createDeveloperDTOWithValues(developerId, developerName);

        when(developerRepository.findById(developerId)).thenReturn(Optional.of(developer));
        when(developerMapper.toDTO(developer)).thenReturn(developerDTO);

        // Act
        DeveloperDTO result = developerService.findById(developerId);

        // Assert
        assertNotNull(result);
        assertEquals(developerId, result.getId());
        assertEquals(developerName, result.getName());
    }

    @Test
    @DisplayName("CP07 - Buscar desarrollador por ID inexistente")
    void findById_nonExistingId_throwsException() {
        // Arrange
        when(developerRepository.findById(developerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> developerService.findById(developerId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Developer not found with id"));
    }

    @Test
    @DisplayName("CP08 - Buscar desarrollador por nombre existente")
    void findByName_existingName_returnsDeveloper() {
        // Arrange
        Developer developer = createDeveloperWithValues(developerId, developerName);
        DeveloperDTO developerDTO = createDeveloperDTOWithValues(developerId, developerName);

        when(developerRepository.findByName(developerName)).thenReturn(Optional.of(developer));
        when(developerMapper.toDTO(developer)).thenReturn(developerDTO);

        // Act
        DeveloperDTO result = developerService.findByName(developerName);

        // Assert
        assertNotNull(result);
        assertEquals(developerName, result.getName());
    }

    @Test
    @DisplayName("CP09 - Buscar desarrollador por nombre inexistente")
    void findByName_nonExistingName_throwsException() {
        // Arrange
        when(developerRepository.findByName(developerName)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> developerService.findByName(developerName));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Developer not found with name"));
    }

    @Test
    @DisplayName("CP10 - Actualizar desarrollador exitosamente")
    void update_validData_returnsUpdatedDeveloper() {
        // Arrange
        Developer existingDeveloper = createDeveloperWithValues(developerId, developerName);
        DeveloperDTO updateDTO = createDeveloperDTOWithValues(developerId, developerName);
        updateDTO.setName("Updated Developer");

        when(developerRepository.findById(developerId)).thenReturn(Optional.of(existingDeveloper));
        when(developerRepository.findByName("Updated Developer")).thenReturn(Optional.empty());
        when(developerRepository.save(existingDeveloper)).thenReturn(existingDeveloper);
        when(developerMapper.toDTO(existingDeveloper)).thenReturn(updateDTO);
        doNothing().when(developerMapper).updateEntity(updateDTO, existingDeveloper);

        // Act
        DeveloperDTO result = developerService.update(developerId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Developer", result.getName());
        verify(developerRepository).save(existingDeveloper);
        verify(developerMapper).updateEntity(updateDTO, existingDeveloper);
    }

    @Test
    @DisplayName("CP11 - Actualizar desarrollador inexistente")
    void update_nonExistingId_throwsException() {
        // Arrange
        DeveloperDTO updateDTO = createDeveloperDTOWithValues(developerId, developerName);
        when(developerRepository.findById(developerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> developerService.update(developerId, updateDTO));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Developer not found with id"));
    }

    @Test
    @DisplayName("CP12 - Actualizar desarrollador con nombre duplicado")
    void update_duplicateName_throwsException() {
        // Arrange
        Developer existingDeveloper = createDeveloperWithValues(developerId, developerName);
        UUID anotherId = UUID.randomUUID();
        Developer anotherDeveloper = createDeveloperWithValues(anotherId, "Updated Developer");
        DeveloperDTO updateDTO = createDeveloperDTOWithValues(developerId, developerName);
        updateDTO.setName("Updated Developer");

        when(developerRepository.findById(developerId)).thenReturn(Optional.of(existingDeveloper));
        when(developerRepository.findByName("Updated Developer")).thenReturn(Optional.of(anotherDeveloper));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> developerService.update(developerId, updateDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Developer name already taken"));
    }

    @Test
    @DisplayName("CP13 - Eliminar desarrollador existente")
    void delete_existingDeveloper_deletesSuccessfully() {
        // Arrange
        when(developerRepository.existsById(developerId)).thenReturn(true);

        // Act
        developerService.delete(developerId);

        // Assert
        verify(developerRepository).deleteById(developerId);
    }

    @Test
    @DisplayName("CP14 - Eliminar desarrollador inexistente")
    void delete_nonExistingDeveloper_throwsException() {
        // Arrange
        when(developerRepository.existsById(developerId)).thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> developerService.delete(developerId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Developer not found with id"));
    }
}
