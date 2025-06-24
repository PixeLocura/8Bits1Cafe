package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.security.UserPrincipal;
import com.pixelocura.bitscafe.service.AdminDeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Developers", description = "API de Gestión de Desarrolladores")
@RequiredArgsConstructor
@RestController
@RequestMapping("/developers")
public class AdminDeveloperController {
    private final AdminDeveloperService adminDeveloperService;

    @Operation(summary = "Listar todos los desarrolladores", description = "Obtiene una lista de todos los desarrolladores en el sistema.\nDevuelve todos los objetos Developer con sus atributos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de desarrolladores obtenida exitosamente", content = @Content(schema = @Schema(implementation = DeveloperDTO.class), mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<DeveloperDTO>> list() {
        List<DeveloperDTO> developers = adminDeveloperService.findAll();
        return ResponseEntity.ok(developers);
    }

    @Operation(summary = "Obtener desarrolladores paginados", description = "Obtiene una lista paginada de desarrolladores.\nTamaño de página predeterminado: 5, ordenado por nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de desarrolladores obtenida exitosamente", content = @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json"))
    })
    @GetMapping("/page")
    public ResponseEntity<Page<DeveloperDTO>> paginate(@PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<DeveloperDTO> developers = adminDeveloperService.paginate(pageable);
        return ResponseEntity.ok(developers);
    }

    @Operation(summary = "Obtener desarrollador por ID", description = "Recupera un desarrollador por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador encontrado", content = @Content(schema = @Schema(implementation = DeveloperDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDTO> get(@PathVariable UUID id) {
        DeveloperDTO developer = adminDeveloperService.findById(id);
        return ResponseEntity.ok(developer);
    }

    @Operation(summary = "Obtener desarrollador por nombre", description = "Recupera un desarrollador por su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador encontrado", content = @Content(schema = @Schema(implementation = DeveloperDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado", content = @Content)
    })
    @GetMapping("/by-name/{name}")
    public ResponseEntity<DeveloperDTO> getByName(@PathVariable String name) {
        DeveloperDTO developer = adminDeveloperService.findByName(name);
        return ResponseEntity.ok(developer);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar desarrollador", description = "Actualiza la información de un desarrollador existente por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador actualizado exitosamente", content = @Content(schema = @Schema(implementation = DeveloperDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o el nombre ya está en uso por otro desarrollador", content = @Content),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDTO> update(@PathVariable UUID id, @Valid @RequestBody DeveloperDTO developerDTO) {
        DeveloperDTO updatedDeveloper = adminDeveloperService.update(id, developerDTO);
        return ResponseEntity.ok(updatedDeveloper);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar desarrollador", description = "Elimina un desarrollador del sistema por su UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Desarrollador eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminDeveloperService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @Operation(summary = "Create developer profile", description = "Creates a developer profile for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Developer profile created successfully", content = @Content(schema = @Schema(implementation = DeveloperDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input or developer profile already exists", content = @Content),
            @ApiResponse(responseCode = "403", description = "User does not have developer role", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DeveloperDTO> createDeveloperProfile(@Valid @RequestBody DeveloperDTO developerDTO,
            @AuthenticationPrincipal UserPrincipal userDetails) {
        DeveloperDTO createdProfile = adminDeveloperService.createDeveloperProfile(developerDTO, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }
}
