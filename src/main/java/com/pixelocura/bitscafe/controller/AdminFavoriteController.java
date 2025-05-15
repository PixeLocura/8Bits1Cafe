package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.FavoriteDTO;
import com.pixelocura.bitscafe.service.AdminFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AdminFavoriteController {

    private final AdminFavoriteService favoriteService;

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<?> addFavorite(@PathVariable UUID userId, @RequestBody FavoriteDTO dto) {
        dto.setUserId(userId);
        favoriteService.addFavorite(dto);
        return ResponseEntity.ok("Juego agregado a favoritos");
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<FavoriteDTO>> getFavorites(@PathVariable UUID userId) {
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    @DeleteMapping("/{userId}/favorites/{gameId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable UUID userId, @PathVariable UUID gameId) {
        favoriteService.removeFavorite(userId, gameId);
        return ResponseEntity.ok("Juego eliminado de favoritos");
    }
}
