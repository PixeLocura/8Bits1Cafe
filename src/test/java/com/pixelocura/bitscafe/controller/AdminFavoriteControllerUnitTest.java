package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.FavoriteDTO;
import com.pixelocura.bitscafe.service.AdminFavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminFavoriteControllerUnitTest {

    @Mock
    private AdminFavoriteService favoriteService;

    @InjectMocks
    private AdminFavoriteController favoriteController;

    private UUID userId;
    private UUID gameId;
    private FavoriteDTO sampleFavorite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        gameId = UUID.randomUUID();

        sampleFavorite = new FavoriteDTO();
        sampleFavorite.setUserId(userId);
        sampleFavorite.setGameId(gameId);
    }

    @Test
    @DisplayName("CP01 - Agregar juego a favoritos")
    void addFavorite_returnsOkMessage() {
        // Act
        ResponseEntity<?> response = favoriteController.addFavorite(userId, sampleFavorite);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juego agregado a favoritos", response.getBody());

        // Verifica que el servicio fue llamado con el DTO correcto (con el userId asignado)
        verify(favoriteService, times(1)).addFavorite(sampleFavorite);
        assertEquals(userId, sampleFavorite.getUserId());
    }

    @Test
    @DisplayName("CP02 - Obtener juegos favoritos de un usuario")
    void getFavorites_returnsListOfFavorites() {
        // Arrange
        when(favoriteService.getFavorites(userId)).thenReturn(List.of(sampleFavorite));

        // Act
        ResponseEntity<List<FavoriteDTO>> response = favoriteController.getFavorites(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(gameId, response.getBody().get(0).getGameId());

        verify(favoriteService, times(1)).getFavorites(userId);
    }

    @Test
    @DisplayName("CP03 - Eliminar juego de favoritos")
    void deleteFavorite_returnsOkMessage() {
        // Act
        ResponseEntity<?> response = favoriteController.deleteFavorite(userId, gameId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juego eliminado de favoritos", response.getBody());

        verify(favoriteService, times(1)).removeFavorite(userId, gameId);
    }
}
