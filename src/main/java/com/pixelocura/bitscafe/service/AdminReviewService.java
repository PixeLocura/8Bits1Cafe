package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminReviewService {
    List<Review> findAll();
    Page<Review> paginate(Pageable pageable);
    Review create(Review review);
    Review findById(User user, Game game);
    Review update(User user, Game game, Review updatedReview);
    void delete(User user, Game game);
}
