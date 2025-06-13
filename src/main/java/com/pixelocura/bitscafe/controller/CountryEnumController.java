package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.enums.Country;
import com.pixelocura.bitscafe.service.CountryEnumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Countries", description = "API para la gestión de países soportados en el sistema")
@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryEnumController {
    private final CountryEnumService countryEnumService;

    @Operation(summary = "Listar códigos ISO de países",
              description = "Obtiene una lista de los códigos ISO de todos los países soportados en el sistema.\nRetorna un array con los códigos, por ejemplo: ['US', 'ES', 'MX', ...]")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de códigos ISO obtenida exitosamente",
                        content = @Content(schema = @Schema(implementation = Country.class),
                        mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryEnumService.getAllCountries());
    }

    @Operation(summary = "Obtener detalles de todos los países", description = "Obtiene un mapa detallado de todos los países soportados.\nPara cada país retorna: código ISO, nombre en español, nombre local y URL de su bandera.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de países obtenidos exitosamente", content = @Content(schema = @Schema(implementation = Map.class), mediaType = "application/json"))
    })
    @GetMapping("/names")
    public ResponseEntity<Map<String, Map<String, String>>> getCountryDetails() {
        return ResponseEntity.ok(countryEnumService.getCountryDetailsMap());
    }

    @Operation(summary = "Obtener país por código ISO", description = "Recupera los detalles de un país por su código ISO (ejemplo: US, ES, MX).\nLa búsqueda no distingue entre mayúsculas y minúsculas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País encontrado", content = @Content(schema = @Schema(implementation = Map.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "País no encontrado para el código ISO proporcionado", content = @Content)
    })
    @GetMapping("/isocode/{code}")
    public ResponseEntity<Map<String, String>> getCountryByIsoCode(@PathVariable String code) {
        Optional<Country> country = countryEnumService.getCountryByIsoCode(code);
        return country.map(c -> ResponseEntity.ok(c.toDetailsMap()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
