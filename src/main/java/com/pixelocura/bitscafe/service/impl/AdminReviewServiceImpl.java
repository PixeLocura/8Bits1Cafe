package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.mapper.ReviewMapper;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.ReviewRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminReviewServiceImpl implements AdminReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> paginate(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    @Transactional
    public ReviewDTO create(ReviewDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + reviewDTO.getUserId()));

        Game game = gameRepository.findById(reviewDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Juego no encontrado con ID: " + reviewDTO.getGameId()));

        // Validar reseña única
        if (reviewRepository.existsByUserAndGame(user, game)) {
            throw new RuntimeException("El usuario ya tiene una reseña para este juego");
        }

        Review review = reviewMapper.toEntity(reviewDTO, user, game);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDTO findById(User user, Game game) {
        Review review = reviewRepository.findByUserAndGame(user, game)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO update(User user, Game game, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findByUserAndGame(user, game)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));

        existingReview.setRating(reviewDTO.getRating());
        existingReview.setComment(reviewDTO.getComment());

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    @Transactional
    public void delete(User user, Game game) {
        Review review = reviewRepository.findByUserAndGame(user, game)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        reviewRepository.delete(review);
    }

    // ---- Métodos adicionales ----
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findByUser(User user) {
        return reviewRepository.findByUser(user).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findByGame(Game game) {
        return reviewRepository.findByGame(game).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRatingByGame(Game game) {
        return reviewRepository.getAverageRatingByGame(game);
    }
}