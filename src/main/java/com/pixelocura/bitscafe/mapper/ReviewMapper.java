package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setUserId(review.getUser().getId());
        dto.setGameId(review.getGame().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        return dto;
    }

    public Review toEntity(ReviewDTO dto, User user, Game game) {
        Review review = new Review();
        review.setUser(user);
        review.setGame(game);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }
}