package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.FavoriteDTO;
import com.pixelocura.bitscafe.service.AdminFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "Favorite", description = "Gestión de juegos favoritos de los usuarios")
public class AdminFavoriteController {

    private final AdminFavoriteService favoriteService;

    @Operation(summary = "Agregar un juego a favoritos", description = "Permite agregar un juego a la lista de favoritos de un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego agregado correctamente a favoritos"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario o juego no encontrado", content = @Content)
    })
    @PostMapping("/{userId}/favorites")
    public ResponseEntity<?> addFavorite(
            @PathVariable UUID userId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del favorito a agregar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FavoriteDTO.class))
            ) FavoriteDTO dto
    ) {
        dto.setUserId(userId);
        favoriteService.addFavorite(dto);
        return ResponseEntity.ok("Juego agregado a favoritos");
    }

    @Operation(summary = "Listar juegos favoritos de un usuario", description = "Devuelve una lista de los juegos que el usuario ha marcado como favoritos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de juegos favoritos obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<FavoriteDTO>> getFavorites(@PathVariable UUID userId) {
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    @Operation(summary = "Eliminar un juego de favoritos", description = "Elimina un juego específico de la lista de favoritos del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego eliminado de favoritos correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario o juego no encontrado", content = @Content)
    })
    @DeleteMapping("/{userId}/favorites/{gameId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable UUID userId, @PathVariable UUID gameId) {
        favoriteService.removeFavorite(userId, gameId);
        return ResponseEntity.ok("Juego eliminado de favoritos");
    }
}
