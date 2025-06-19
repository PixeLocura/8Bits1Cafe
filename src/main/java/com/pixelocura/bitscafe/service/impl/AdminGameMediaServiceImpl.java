package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.GameMedia;
import com.pixelocura.bitscafe.repository.GameMediaRepository;
import com.pixelocura.bitscafe.service.AdminGameMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminGameMediaServiceImpl implements AdminGameMediaService {

    private final GameMediaRepository gameMediaRepository;

    // Obtener todos los registros
    @Override
    public List<GameMedia> findAll() {
        return gameMediaRepository.findAll();
    }

    // Paginación
    @Override
    public Page<GameMedia> paginate(Pageable pageable) {
        return gameMediaRepository.findAll(pageable);
    }

    //  Crear nuevo GameMedia
    @Override
    public GameMedia create(GameMedia gameMedia) {
        return gameMediaRepository.save(gameMedia);
    }

    //  Buscar por ID
    @Override
    public GameMedia findById(UUID id) {
        return gameMediaRepository.findById(id).orElse(null);
    }

    //  Actualizar por ID
    @Override
    public GameMedia update(UUID id, GameMedia updatedGameMedia) {
        return gameMediaRepository.findById(id)
                .map(existing -> {
                    existing.setGame(updatedGameMedia.getGame());
                    existing.setMediaLink(updatedGameMedia.getMediaLink());
                    // ⚠️ NO toques el campo creationDate
                    return gameMediaRepository.save(existing);
                })
                .orElse(null);
    }


    //  Eliminar por ID
    @Override
    public void delete(UUID id) {
        gameMediaRepository.deleteById(id);
    }
}
