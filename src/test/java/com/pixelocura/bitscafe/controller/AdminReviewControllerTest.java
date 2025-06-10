package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminReviewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // Estos Id son los que creé en postman
    private final UUID gameId = UUID.fromString("9d2628c0-506b-4607-bbc8-edd333e647c7");
    private final UUID userId = UUID.fromString("15acd81c-47f8-4b0c-b2f9-fb102ab33903");

    @Test
    public void testReviewCRUD() {
        // 1. Crear review
        ReviewDTO review = new ReviewDTO();
        review.setRating(4.8);
        review.setComment("Test automático desde Spring Boot");
        review.setGameId(gameId);
        review.setUserId(userId);


        ResponseEntity<ReviewDTO> createResponse = restTemplate.postForEntity(
                "/reviews/games/" + gameId + "/users/" + userId,
                review,
                ReviewDTO.class
        );

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals(4.8, createResponse.getBody().getRating());
        assertEquals("Test automático desde Spring Boot", createResponse.getBody().getComment());

        // 2. Obtener review por usuario y juego
        ResponseEntity<ReviewDTO> getResponse = restTemplate.getForEntity(
                "/reviews/games/" + gameId + "/users/" + userId,
                ReviewDTO.class
        );

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(gameId, getResponse.getBody().getGameId());
        assertEquals(userId, getResponse.getBody().getUserId());

        // 3. Actualizar review
        ReviewDTO updatedReview = new ReviewDTO();
        updatedReview.setRating(5.0);
        updatedReview.setComment("Comentario actualizado desde test");
        updatedReview.setGameId(gameId);
        updatedReview.setUserId(userId);

        ResponseEntity<ReviewDTO> updateResponse = restTemplate.exchange(
                "/reviews/games/" + gameId + "/users/" + userId,
                HttpMethod.PUT,
                new HttpEntity<>(updatedReview),
                ReviewDTO.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals(5.0, updateResponse.getBody().getRating());
        assertTrue(updateResponse.getBody().getComment().contains("actualizado"));

        // 4. Obtener todos los reviews
        ResponseEntity<List<ReviewDTO>> getAllResponse = restTemplate.exchange(
                "/reviews",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReviewDTO>>() {}
        );

        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        assertTrue(getAllResponse.getBody().size() > 0);

        // 5. Eliminar review
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/reviews/games/" + gameId + "/users/" + userId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // 6. Verificar eliminación
        ResponseEntity<ReviewDTO> getDeletedResponse = restTemplate.getForEntity(
                "/reviews/games/" + gameId + "/users/" + userId,
                ReviewDTO.class
        );

        assertEquals(HttpStatus.NOT_FOUND, getDeletedResponse.getStatusCode());
    }
}
