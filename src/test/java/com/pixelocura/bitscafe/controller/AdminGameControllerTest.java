package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.service.AdminGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminGameControllerTest {

    @Mock
    private AdminGameService adminGameService;

    @InjectMocks
    private AdminGameController adminGameController;

    private UUID gameId;
    private UUID developerId;
    private GameDTO sampleGame;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameId = UUID.randomUUID();
        developerId = UUID.randomUUID();

        sampleGame = new GameDTO();
        sampleGame.setId(gameId);
        sampleGame.setDeveloper_id(developerId);
        sampleGame.setTitle("Test Game");
        sampleGame.setDescription("Test Description");
        sampleGame.setPrice(29.99);
        sampleGame.setCoverUrl("https://example.com/cover.jpg");
        sampleGame.setReleaseDate(ZonedDateTime.now());
    }

    @Test
    @DisplayName("CP01 - Obtener todos los juegos")
    void list_returnsAllGames() {
        when(adminGameService.findAll()).thenReturn(List.of(sampleGame));

        ResponseEntity<List<GameDTO>> response = adminGameController.list();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(adminGameService, times(1)).findAll();
    }

    @Test
    @DisplayName("CP02 - Obtener juegos paginados")
    void paginate_returnsPagedGames() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<GameDTO> pagedGames = new PageImpl<>(List.of(sampleGame), pageable, 1);

        when(adminGameService.paginate(pageable)).thenReturn(pagedGames);

        ResponseEntity<Page<GameDTO>> response = adminGameController.paginate(pageable);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());

        verify(adminGameService, times(1)).paginate(pageable);
    }

    @Test
    @DisplayName("CP03 - Crear un juego")
    void create_returnsCreatedGame() {
        when(adminGameService.create(sampleGame)).thenReturn(sampleGame);

        ResponseEntity<GameDTO> response = adminGameController.create(sampleGame);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Game", response.getBody().getTitle());

        verify(adminGameService, times(1)).create(sampleGame);
    }

    @Test
    @DisplayName("CP04 - Obtener juego por ID (existe)")
    void getById_returnsGameIfExists() {
        when(adminGameService.findById(gameId)).thenReturn(sampleGame);

        ResponseEntity<GameDTO> response = adminGameController.getById(gameId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(gameId, response.getBody().getId());

        verify(adminGameService, times(1)).findById(gameId);
    }

    @Test
    @DisplayName("CP05 - Obtener juego por ID (no existe)")
    void getById_returnsNotFoundIfGameIsNull() {
        when(adminGameService.findById(gameId)).thenReturn(null);

        ResponseEntity<GameDTO> response = adminGameController.getById(gameId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(adminGameService, times(1)).findById(gameId);
    }

    @Test
    @DisplayName("CP06 - Actualizar juego")
    void update_returnsUpdatedGame() {
        GameDTO updatedGame = new GameDTO();
        updatedGame.setDeveloper_id(developerId);
        updatedGame.setTitle("Updated Game");
        updatedGame.setDescription("Updated description");
        updatedGame.setPrice(19.99);
        updatedGame.setCoverUrl("https://example.com/updated.jpg");
        updatedGame.setReleaseDate(ZonedDateTime.now());

        when(adminGameService.update(gameId, updatedGame)).thenReturn(updatedGame);

        ResponseEntity<GameDTO> response = adminGameController.update(gameId, updatedGame);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Game", response.getBody().getTitle());

        verify(adminGameService, times(1)).update(gameId, updatedGame);
    }

    @Test
    @DisplayName("CP07 - Eliminar juego")
    void delete_returnsNoContent() {
        ResponseEntity<Void> response = adminGameController.delete(gameId);

        assertEquals(204, response.getStatusCodeValue());
        verify(adminGameService, times(1)).delete(gameId);
    }
}
