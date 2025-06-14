package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.service.AdminGameService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name="Games", description = "API de Gestion de juegos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
public class AdminGameController {
    private final AdminGameService adminGameService;

    @Operation(summary = "Listar todos los juegos", description = "Obtiene una lista de todos los juegos en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de juegos obtenida exitosamente", content = @Content(schema = @Schema(implementation = GameDTO.class), mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<GameDTO>> list() {
        List<GameDTO> games = adminGameService.findAll();
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Obtener juagos paginados", description = "Obtiene una lista paginada de juegos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de juegos obtenida exitosamente", content = @Content(schema = @Schema(implementation = Page.class),mediaType = "application/json"))
    })
    @GetMapping("/page")
    public ResponseEntity<Page<GameDTO>> paginate(@PageableDefault(size = 5, sort = "title") Pageable pageable) {
        Page<GameDTO> games = adminGameService.paginate(pageable);
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Crear nuevo juego", description = "Crea un nuevo juego en el sistema con la información proporcionada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juego creado exitosamente", content = @Content(schema = @Schema(implementation = GameDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o ya existe un juego con el mismo nombre", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GameDTO> create(@Valid @RequestBody GameDTO gameDTO) {
        GameDTO createdGame = adminGameService.create(gameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
    }

    @Operation(summary = "Obtener juego por ID", description = "Recupera un juego por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego encontrado", content = @Content(schema = @Schema(implementation = GameDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getById(@PathVariable UUID id) {
        GameDTO game = adminGameService.findById(id);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @Operation(summary = "Actualizar juego", description = "Actualiza la información de un juego existente por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego actualizado exitosamente", content = @Content(schema = @Schema(implementation = GameDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o el nombre ya está en uso por otro juego", content = @Content),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> update(@PathVariable UUID id, @Valid @RequestBody GameDTO game) {
        GameDTO updatedGame = adminGameService.update(id, game);
        return ResponseEntity.ok(updatedGame);
    }

    @Operation(summary = "Eliminar juego", description = "Elimina un juego del sistema por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Juego eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminGameService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
