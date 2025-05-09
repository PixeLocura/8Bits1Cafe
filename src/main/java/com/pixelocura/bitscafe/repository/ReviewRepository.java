package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Review.ReviewId> {
    List<Review> findByUser(User user);
    List<Review> findByGame(Game game);
    boolean existsByUserAndGame(User user, Game game);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.game = :game")
    Double getAverageRatingByGame(@Param("game") Game game);
}
