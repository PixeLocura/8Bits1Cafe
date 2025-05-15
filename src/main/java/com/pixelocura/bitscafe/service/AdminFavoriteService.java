package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.dto.FavoriteDTO;

import java.util.List;
import java.util.UUID;

public interface AdminFavoriteService {
    void addFavorite(FavoriteDTO favoriteDTO);
    void removeFavorite(UUID userId, UUID gameId);
    List<FavoriteDTO> getFavorites(UUID userId);
}
