package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.entity.GameMedia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminGameMediaService {
    List<GameMedia> findAll();
    Page<GameMedia> paginate(Pageable pageable);
    GameMedia create(GameMedia gameMedia);
    GameMedia findById(UUID id);
    GameMedia update(UUID id, GameMedia updatedGameMedia);
    void delete(UUID id);
}
