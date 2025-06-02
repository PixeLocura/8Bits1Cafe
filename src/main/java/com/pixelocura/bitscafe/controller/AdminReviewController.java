package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.service.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    // GET /api/v1/reviews
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = adminReviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // GET /api/v1/games/{gameId}/reviews
    @GetMapping("/games/{gameId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByGame(@PathVariable UUID gameId) {
        List<ReviewDTO> reviews = adminReviewService.getAllReviews().stream()
                .filter(r -> r.getGameId().equals(gameId))
                .toList();
        return ResponseEntity.ok(reviews);
    }

    // POST /api/v1/games/{gameId}/reviews
    @PostMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<ReviewDTO> createReview(
            @PathVariable UUID gameId,
            @PathVariable UUID userId,
            @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setGameId(gameId);
        reviewDTO.setUserId(userId);
        ReviewDTO created = adminReviewService.createReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<ReviewDTO> getReviewByGameAndUser(
            @PathVariable UUID gameId,
            @PathVariable UUID userId) {
        ReviewDTO review = adminReviewService.getReviewByUserAndGame(userId, gameId);
        return ResponseEntity.ok(review);
    }

    // PUT /api/v1/games/{gameId}/users/{userId}/reviews
    @PutMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<ReviewDTO> updateReviewByGameAndUser(
            @PathVariable UUID gameId,
            @PathVariable UUID userId,
            @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setGameId(gameId);
        reviewDTO.setUserId(userId);
        ReviewDTO updated = adminReviewService.updateReview(reviewDTO);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/v1/reviews/{reviewId}/reviews
    @DeleteMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<Void> deleteReviewByGameAndUser(
            @PathVariable UUID gameId,
            @PathVariable UUID userId) {
        adminReviewService.deleteReview(userId, gameId);
        return ResponseEntity.noContent().build();
    }
}
