package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminDeveloperControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDeveloperCRUD() {
        // Create a developer
        DeveloperDTO developerToCreate = new DeveloperDTO();
        developerToCreate.setName("Test Developer");
        developerToCreate.setDescription("A test developer for unit testing");
        developerToCreate.setWebsite("https://www.testdeveloper.com");

        ResponseEntity<DeveloperDTO> createResponse = restTemplate.postForEntity(
                "/developers", developerToCreate, DeveloperDTO.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals("Test Developer", createResponse.getBody().getName());
        UUID developerId = createResponse.getBody().getId();

        // Get developer by ID
        ResponseEntity<DeveloperDTO> getResponse = restTemplate.getForEntity(
                "/developers/" + developerId, DeveloperDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(developerId, getResponse.getBody().getId());

        // Get developer by name
        ResponseEntity<DeveloperDTO> getByNameResponse = restTemplate.getForEntity(
                "/developers/by-name/Test Developer", DeveloperDTO.class);

        assertEquals(HttpStatus.OK, getByNameResponse.getStatusCode());
        assertEquals("Test Developer", getByNameResponse.getBody().getName());

        // Update developer
        DeveloperDTO updateDTO = new DeveloperDTO();
        updateDTO.setName("Updated Test Developer");
        updateDTO.setDescription("Updated description");
        updateDTO.setWebsite("https://www.updated-dev.com");

        ResponseEntity<DeveloperDTO> updateResponse = restTemplate.exchange(
                "/developers/" + developerId,
                HttpMethod.PUT,
                new HttpEntity<>(updateDTO),
                DeveloperDTO.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Updated Test Developer", updateResponse.getBody().getName());
        assertEquals("Updated description", updateResponse.getBody().getDescription());

        // Get all developers
        ResponseEntity<List<DeveloperDTO>> getAllResponse = restTemplate.exchange(
                "/developers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DeveloperDTO>>() {});

        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        assertTrue(getAllResponse.getBody().size() >= 1);

        // Delete developer
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/developers/" + developerId,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Verify deletion
        ResponseEntity<DeveloperDTO> getDeletedResponse = restTemplate.getForEntity(
                "/developers/" + developerId, DeveloperDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, getDeletedResponse.getStatusCode());
    }
}
