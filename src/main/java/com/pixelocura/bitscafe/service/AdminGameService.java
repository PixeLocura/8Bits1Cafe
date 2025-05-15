package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public interface AdminGameService {
    List<Game> findAll();
    Page<Game> paginate(Pageable pageable);
    Game create(Game game);
    Game findById(UUID id);
    Game update(UUID id, Game updatedGame);
    void delete(UUID id);
}
