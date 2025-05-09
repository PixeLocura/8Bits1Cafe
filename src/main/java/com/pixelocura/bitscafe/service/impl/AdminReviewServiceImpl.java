package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.service.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminReviewServiceImpl implements AdminReviewService {
    @Override
    public List<Review> findAll() {
        return List.of();
    }

    @Override
    public Page<Review> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Review create(Review review) {
        return null;
    }

    @Override
    public Review findById(User user, Game game) {
        return null;
    }

    @Override
    public Review update(User user, Game game, Review updatedReview) {
        return null;
    }

    @Override
    public void delete(User user, Game game) {

    }
}
