package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminGameService {
    List<Game> findAll();
    Page<Game> paginate(Pageable pageable);
    Game create(Game game);
    Game findById(UUID id);
    Game update(UUID id, Game updatedGame);
    void delete(UUID id);
}
