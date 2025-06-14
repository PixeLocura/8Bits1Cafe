package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.service.AdminReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reviews", description = "API de Gestión de Reseñas de Juegos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @Operation(summary = "Listar todas las reseñas", description = "Obtiene una lista de todas las reseñas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = adminReviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Listar reseñas por juego", description = "Obtiene todas las reseñas asociadas a un juego en específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas del juego obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class)))
    })
    @GetMapping("/games/{gameId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByGame(@PathVariable UUID gameId) {
        List<ReviewDTO> reviews = adminReviewService.getAllReviews().stream()
                .filter(r -> r.getGameId().equals(gameId))
                .toList();
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña para un juego realizada por un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
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

    @Operation(summary = "Obtener reseña de usuario por juego", description = "Obtiene una reseña específica escrita por un usuario para un juego.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content)
    })
    @GetMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<ReviewDTO> getReviewByGameAndUser(
            @PathVariable UUID gameId,
            @PathVariable UUID userId) {
        ReviewDTO review = adminReviewService.getReviewByUserAndGame(userId, gameId);
        return ResponseEntity.ok(review);
    }

    @Operation(summary = "Actualizar reseña", description = "Actualiza una reseña existente de un usuario para un juego.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content)
    })
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

    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña específica escrita por un usuario para un juego.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content)
    })
    @DeleteMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<Void> deleteReviewByGameAndUser(
            @PathVariable UUID gameId,
            @PathVariable UUID userId) {
        adminReviewService.deleteReview(userId, gameId);
        return ResponseEntity.noContent().build();
    }
}

