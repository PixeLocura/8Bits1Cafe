package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.FavoriteDTO;
import com.pixelocura.bitscafe.model.entity.Favorite;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.FavoriteRepository;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.impl.AdminFavoriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminFavoriteServiceUnitTest {

    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private AdminFavoriteServiceImpl adminFavoriteService;

    private UUID userId;
    private UUID gameId;
    private FavoriteDTO favoriteDTO;
    private User user;
    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        gameId = UUID.randomUUID();

        favoriteDTO = new FavoriteDTO();
        favoriteDTO.setUserId(userId);
        favoriteDTO.setGameId(gameId);

        user = new User();
        user.setId(userId);

        game = new Game();
        game.setId(gameId);
    }

    @Test
    void addFavorite_newFavorite_savesFavorite() {
        when(favoriteRepository.existsByUser_IdAndGame_Id(userId, gameId)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        adminFavoriteService.addFavorite(favoriteDTO);

        verify(favoriteRepository).save(any(Favorite.class));
    }

    @Test
    void addFavorite_alreadyExists_doesNothing() {
        when(favoriteRepository.existsByUser_IdAndGame_Id(userId, gameId)).thenReturn(true);

        adminFavoriteService.addFavorite(favoriteDTO);

        verify(favoriteRepository, never()).save(any());
    }

    @Test
    void removeFavorite_callsRepositoryDelete() {
        adminFavoriteService.removeFavorite(userId, gameId);
        verify(favoriteRepository, times(1)).deleteByUser_IdAndGame_Id(userId, gameId);
    }

    @Test
    void getFavorites_returnsMappedDTOs() {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setGame(game);

        when(favoriteRepository.findByUser_Id(userId)).thenReturn(List.of(favorite));

        var result = adminFavoriteService.getFavorites(userId);

        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(gameId, result.get(0).getGameId());
    }
}
