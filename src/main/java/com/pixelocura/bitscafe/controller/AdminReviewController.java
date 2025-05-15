package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.service.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getByUser(@PathVariable UUID userId) {
        User user = new User(); user.setId(userId);
        return ResponseEntity.ok(adminReviewService.findByUser(user));
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<ReviewDTO>> getByGame(@PathVariable UUID gameId) {
        Game game = new Game(); game.setId(gameId);
        return ResponseEntity.ok(adminReviewService.findByGame(game));
    }

    @GetMapping("/game/{gameId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID gameId) {
        Game game = new Game(); game.setId(gameId);
        return ResponseEntity.ok(adminReviewService.getAverageRatingByGame(game));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(adminReviewService.create(reviewDTO));
    }

    @PutMapping("/user/{userId}/game/{gameId}")
    public ResponseEntity<ReviewDTO> update(
            @PathVariable UUID userId,
            @PathVariable UUID gameId,
            @RequestBody ReviewDTO reviewDTO) {
        User user = new User(); user.setId(userId);
        Game game = new Game(); game.setId(gameId);
        return ResponseEntity.ok(adminReviewService.update(user, game, reviewDTO));
    }

    @DeleteMapping("/user/{userId}/game/{gameId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID userId,
            @PathVariable UUID gameId) {
        User user = new User(); user.setId(userId);
        Game game = new Game(); game.setId(gameId);
        adminReviewService.delete(user, game);
        return ResponseEntity.noContent().build();
    }
}
