package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setUserId(review.getUser().getId());
        dto.setGameId(review.getGame().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        // Campos extras para mostrar en la respuesta
        dto.setUserName(review.getUser().getUsername());  // o el campo que uses
        dto.setGameTitle(review.getGame().getTitle());
        dto.setCreatedAt(review.getCreationDate());
        dto.setUpdatedAt(review.getUpdateDate());

        return dto;
    }

    public Review toEntity(ReviewDTO dto, User user, Game game) {
        Review review = new Review();
        review.setUser(user);
        review.setGame(game);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        review.setCreationDate(dto.getCreatedAt() != null ? dto.getCreatedAt() : ZonedDateTime.now(ZoneOffset.UTC));
        review.setUpdateDate(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : ZonedDateTime.now(ZoneOffset.UTC));

        return review;
    }
}