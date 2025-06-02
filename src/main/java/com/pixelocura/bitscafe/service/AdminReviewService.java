package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.ReviewDTO;

import java.util.List;
import java.util.UUID;

public interface AdminReviewService {
    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewByUserAndGame(UUID userId, UUID gameId);
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(ReviewDTO reviewDTO);
    void deleteReview(UUID userId, UUID gameId);
}
