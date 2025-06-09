package com.pixelocura.bitscafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.dto.GameDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminGameControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGameCRUD() {
        ResponseEntity<List> response = restTemplate.getForEntity("/developers", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());

        // Paso 2: Convertir primer resultado a DeveloperDTO
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Soporte para ZonedDateTime

        LinkedHashMap<String, Object> devMap = (LinkedHashMap<String, Object>) response.getBody().get(0);
        DeveloperDTO dev = mapper.convertValue(devMap, DeveloperDTO.class);
        UUID developerId = dev.getId();

        // Paso 3: Usar developerId para crear el juego
        GameDTO gameToCreate = new GameDTO();
        gameToCreate.setDeveloper_id(developerId);
        gameToCreate.setTitle("Test Game");
        gameToCreate.setDescription("A test game for unit testing");
        gameToCreate.setPrice(29.99);
        gameToCreate.setCoverUrl("https://example.com/testgame.jpg");
        gameToCreate.setReleaseDate(ZonedDateTime.now());

        ResponseEntity<GameDTO> createResponse = restTemplate.postForEntity(
                "/games", gameToCreate, GameDTO.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals("Test Game", createResponse.getBody().getTitle());

        UUID gameId = createResponse.getBody().getId();

        // Obtener juego por ID
        ResponseEntity<GameDTO> getResponse = restTemplate.getForEntity(
                "/games/" + gameId, GameDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(gameId, getResponse.getBody().getId());

        // Actualizar juego
        GameDTO updateDTO = new GameDTO();
        updateDTO.setDeveloper_id(gameToCreate.getDeveloper_id());
        updateDTO.setTitle("Updated Test Game");
        updateDTO.setDescription("Updated description");
        updateDTO.setPrice(19.99);
        updateDTO.setCoverUrl("https://example.com/updated.jpg");
        updateDTO.setReleaseDate(ZonedDateTime.now());

        ResponseEntity<GameDTO> updateResponse = restTemplate.exchange(
                "/games/" + gameId,
                HttpMethod.PUT,
                new HttpEntity<>(updateDTO),
                GameDTO.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Updated Test Game", updateResponse.getBody().getTitle());

        // Obtener todos los juegos
        ResponseEntity<List<GameDTO>> getAllResponse = restTemplate.exchange(
                "/games",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GameDTO>>() {});

        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        assertNotNull(getAllResponse.getBody());
        assertFalse(getAllResponse.getBody().isEmpty());

        // Eliminar juego
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/games/" + gameId,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Verificar eliminación
        ResponseEntity<GameDTO> getDeletedResponse = restTemplate.getForEntity(
                "/games/" + gameId, GameDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, getDeletedResponse.getStatusCode());
    }
}


