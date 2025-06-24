package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.UserDTO;
import com.pixelocura.bitscafe.service.AdminUserService;
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
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users", description = "API de Gestión de Usuarios")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios en el sistema.\nDevuelve todos los objetos Usuario con sus atributos."
    // tags = {"users", "list"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente", content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        List<UserDTO> users = adminUserService.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuarios paginados", description = "Obtiene una lista paginada de usuarios.\nTamaño de página predeterminado: 5, ordenado por nombre de usuario."
    // tags = {"users", "pagination"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de usuarios obtenida exitosamente", content = @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json"))
    })
    @GetMapping("/page")
    public ResponseEntity<Page<UserDTO>> paginate(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        Page<UserDTO> users = adminUserService.paginate(pageable);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema con la información proporcionada."
    // tags = {"users", "create"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o el usuario ya existe con ese nombre de usuario o correo electrónico", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = adminUserService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Obtener usuario por ID", description = "Recupera un usuario por su UUID."
    // tags = {"users", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable UUID id) {
        UserDTO user = adminUserService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuario por nombre de usuario", description = "Recupera un usuario por su nombre de usuario."
    // tags = {"users", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable String username) {
        UserDTO user = adminUserService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuario por correo electrónico", description = "Recupera un usuario por su dirección de correo electrónico."
    // tags = {"users", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email) {
        UserDTO user = adminUserService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente por su UUID."
    // tags = {"users", "update"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente", content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o el nombre de usuario/correo electrónico ya está en uso por otro usuario", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = adminUserService.update(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su UUID."
    // tags = {"users", "delete"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
