package com.pixelocura.bitscafe.repository;

import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.Review;
import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Review.ReviewId> {

    // Métodos existentes (asegúrate de tener estos)
    @Query("SELECT r FROM Review r WHERE r.user = :user AND r.game = :game")
    Optional<Review> findByUserAndGame(@Param("user") User user, @Param("game") Game game);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.user = :user AND r.game = :game")
    boolean existsByUserAndGame(@Param("user") User user, @Param("game") Game game);

    List<Review> findByUser(User user);
    List<Review> findByGame(Game game);

    // ---- NUEVO MÉTODO PARA EL PROMEDIO ----
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.game = :game")
    Double getAverageRatingByGame(@Param("game") Game game);
}
