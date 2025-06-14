package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.GameMediaDTO;
import com.pixelocura.bitscafe.mapper.GameMediaMapper;
import com.pixelocura.bitscafe.model.entity.GameMedia;
import com.pixelocura.bitscafe.service.AdminGameMediaService;
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
import java.util.stream.Collectors;

@Tag(name = "Game Media", description = "API de Gestión de Medios de Juegos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/game-media")
public class AdminGameMediaController {

    private final AdminGameMediaService adminGameMediaService;
    private final GameMediaMapper gameMediaMapper;

    @Operation(summary = "Listar todos los GameMedia", description = "Obtiene todos los registros de medios (imágenes/videos) asociados a juegos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente", content = @Content(schema = @Schema(implementation = GameMediaDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<GameMediaDTO>> list() {
        List<GameMediaDTO> dtoList = adminGameMediaService.findAll()
                .stream()
                .map(gameMediaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "Obtener GameMedia paginados", description = "Obtiene medios de juegos paginados por tamaño y orden.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginación exitosa", content = @Content(schema = @Schema(implementation = GameMediaDTO.class)))
    })
    @GetMapping("/page")
    public ResponseEntity<Page<GameMediaDTO>> paginate(@PageableDefault(size = 5, sort = "creationDate") Pageable pageable) {
        Page<GameMediaDTO> dtoPage = adminGameMediaService.paginate(pageable)
                .map(gameMediaMapper::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @Operation(summary = "Crear nuevo GameMedia", description = "Registra un nuevo medio de juego con el ID del juego relacionado y el link del archivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medio creado exitosamente", content = @Content(schema = @Schema(implementation = GameMediaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GameMediaDTO> create(@Valid @RequestBody GameMediaDTO dto) {
        GameMedia entity = gameMediaMapper.toEntity(dto);
        GameMedia saved = adminGameMediaService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameMediaMapper.toDTO(saved));
    }

    @Operation(summary = "Obtener GameMedia por ID", description = "Busca un medio de juego por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medio encontrado", content = @Content(schema = @Schema(implementation = GameMediaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameMediaDTO> getById(@PathVariable UUID id) {
        GameMedia media = adminGameMediaService.findById(id);
        return media != null ? ResponseEntity.ok(gameMediaMapper.toDTO(media)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar GameMedia", description = "Actualiza el link de un medio de juego existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización exitosa", content = @Content(schema = @Schema(implementation = GameMediaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Medio no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GameMediaDTO> update(@PathVariable UUID id, @Valid @RequestBody GameMediaDTO dto) {
        GameMedia updated = adminGameMediaService.update(id, gameMediaMapper.toEntity(dto));
        return updated != null ? ResponseEntity.ok(gameMediaMapper.toDTO(updated)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar GameMedia", description = "Elimina un medio por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Medio no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminGameMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
