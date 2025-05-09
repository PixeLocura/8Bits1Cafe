package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Favorite;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminFavoriteService {
    List<Favorite> findAll();
    Page<Favorite> paginate(Pageable pageable);
    Favorite create(Favorite favorite);
    Favorite findById(User user, Game game);
    Favorite update(User user, Game game, Favorite updatedFavorite);
    void delete(User user, Game game);
    List<Favorite> findByUser(User user);
    List<Favorite> findByGame(Game game);
}
