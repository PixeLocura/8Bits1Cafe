package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.GameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public interface AdminGameService {
    List<GameDTO> findAll();
    Page<GameDTO> paginate(Pageable pageable);
    GameDTO create(GameDTO game);
    GameDTO findById(UUID id);
    GameDTO update(UUID id, GameDTO updatedGame);
    void delete(UUID id);
}
