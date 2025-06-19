package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.mapper.ReviewMapper;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.ReviewRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.impl.AdminReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceUnitTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private AdminReviewServiceImpl reviewService;

    private UUID userId;
    private UUID gameId;
    private User user;
    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Activamos los @Mock e @InjectMocks

        userId = UUID.randomUUID();
        gameId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setUsername("Luis");

        game = new Game();
        game.setId(gameId);
        game.setTitle("Mario Bros");
    }

    private Review createReview(User user, Game game, double rating, String comment) {
        Review review = new Review();
        review.setUser(user);
        review.setGame(game);
        review.setRating(rating);
        review.setComment(comment);
        return review;
    }

    private ReviewDTO createReviewDTO(UUID gameId, UUID userId, double rating, String comment) {
        ReviewDTO dto = new ReviewDTO();
        dto.setGameId(gameId);
        dto.setUserId(userId);
        dto.setRating(rating);
        dto.setComment(comment);
        return dto;
    }

    @Test
    @DisplayName("CP01 - Listar todos los reviews")
    void getAllReviews_returnsListOfDTOs() {
        Review review = createReview(user, game, 4.5, "Muy bueno");
        ReviewDTO dto = createReviewDTO(gameId, userId, 4.5, "Muy bueno");

        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));
        when(reviewMapper.toDTO(review)).thenReturn(dto);

        List<ReviewDTO> result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(4.5, result.get(0).getRating());
    }

    @Test
    @DisplayName("CP02 - Obtener review por ID compuesto")
    void getReviewByUserAndGame_existingReview_returnsDTO() {
        Review review = createReview(user, game, 4.0, "Bien");
        ReviewDTO dto = createReviewDTO(gameId, userId, 4.0, "Bien");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewRepository.findByUserAndGame(user, game)).thenReturn(Optional.of(review));
        when(reviewMapper.toDTO(review)).thenReturn(dto);

        ReviewDTO result = reviewService.getReviewByUserAndGame(userId, gameId);

        assertNotNull(result);
        assertEquals(gameId, result.getGameId());
        assertEquals(userId, result.getUserId());
    }

    @Test
    @DisplayName("CP03 - Crear review válido")
    void createReview_validData_returnsCreatedDTO() {
        ReviewDTO dto = createReviewDTO(gameId, userId, 4.7, "Excelente");
        Review entity = createReview(user, game, 4.7, "Excelente");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewMapper.toEntity(dto, user, game)).thenReturn(entity);
        when(reviewRepository.save(entity)).thenReturn(entity);
        when(reviewMapper.toDTO(entity)).thenReturn(dto);

        ReviewDTO result = reviewService.createReview(dto);

        assertNotNull(result);
        assertEquals(4.7, result.getRating());
    }

    @Test
    @DisplayName("CP04 - Actualizar review existente")
    void updateReview_existingReview_returnsUpdatedDTO() {
        Review existing = createReview(user, game, 3.0, "Regular");
        ReviewDTO dtoUpdate = createReviewDTO(gameId, userId, 4.9, "Mejoró bastante");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewRepository.findByUserAndGame(user, game)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(existing)).thenReturn(existing);
        when(reviewMapper.toDTO(existing)).thenReturn(dtoUpdate);

        ReviewDTO result = reviewService.updateReview(dtoUpdate);

        assertNotNull(result);
        assertEquals(4.9, result.getRating());
        assertEquals("Mejoró bastante", result.getComment());
    }

    @Test
    @DisplayName("CP05 - Eliminar review existente")
    void deleteReview_existingReview_deletesSuccessfully() {
        Review existing = createReview(user, game, 4.0, "Para borrar");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewRepository.findByUserAndGame(user, game)).thenReturn(Optional.of(existing));

        reviewService.deleteReview(userId, gameId);

        verify(reviewRepository).delete(existing);
    }
}
