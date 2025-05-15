package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.ReviewDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminReviewService {
    List<ReviewDTO> findAll();
    Page<ReviewDTO> paginate(Pageable pageable);
    ReviewDTO create(ReviewDTO reviewDTO);
    ReviewDTO findById(User user, Game game);
    ReviewDTO update(User user, Game game, ReviewDTO reviewDTO);
    void delete(User user, Game game);

    // Nuevos métodos
    List<ReviewDTO> findByUser(User user);
    List<ReviewDTO> findByGame(Game game);
    Double getAverageRatingByGame(Game game);
}