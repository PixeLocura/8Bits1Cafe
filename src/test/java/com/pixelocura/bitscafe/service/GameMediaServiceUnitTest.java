package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.GameMedia;
import com.pixelocura.bitscafe.repository.GameMediaRepository;
import com.pixelocura.bitscafe.service.impl.AdminGameMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameMediaServiceUnitTest {

    @Mock
    private GameMediaRepository gameMediaRepository;

    @InjectMocks
    private AdminGameMediaServiceImpl gameMediaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los @Mock y @InjectMocks
    }

    @Test
    void testCreateGameMedia() {
        // Arrange
        Game game = new Game();
        game.setId(UUID.randomUUID());

        GameMedia media = new GameMedia();
        media.setId(UUID.randomUUID());
        media.setGame(game);
        media.setMediaLink("https://example.com/media.jpg");
        media.setCreationDate(ZonedDateTime.now());

        when(gameMediaRepository.save(any(GameMedia.class))).thenReturn(media);

        // Act
        GameMedia saved = gameMediaService.create(media);

        // Assert
        assertNotNull(saved);
        assertEquals("https://example.com/media.jpg", saved.getMediaLink());
        verify(gameMediaRepository, times(1)).save(any(GameMedia.class));
    }

    @Test
    void testFindAll() {
        // Arrange
        GameMedia media = new GameMedia();
        media.setId(UUID.randomUUID());
        media.setMediaLink("https://example.com/media.png");

        when(gameMediaRepository.findAll()).thenReturn(List.of(media));

        // Act
        List<GameMedia> result = gameMediaService.findAll();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("https://example.com/media.png", result.get(0).getMediaLink());
    }

    @Test
    void testDeleteGameMedia() {
        // Arrange
        UUID mediaId = UUID.randomUUID();
        GameMedia media = new GameMedia();
        media.setId(mediaId);

        when(gameMediaRepository.findById(mediaId)).thenReturn(Optional.of(media));
        doNothing().when(gameMediaRepository).deleteById(mediaId);

        // Act
        gameMediaService.delete(mediaId);

        // Assert
        verify(gameMediaRepository, times(1)).deleteById(mediaId);
    }
}
