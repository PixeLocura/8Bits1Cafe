package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.FavoriteDTO;
import com.pixelocura.bitscafe.model.entity.Favorite;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.FavoriteRepository;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import com.pixelocura.bitscafe.service.AdminFavoriteService;
import com.pixelocura.bitscafe.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AdminFavoriteServiceImpl implements AdminFavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Override
    public void addFavorite(FavoriteDTO favoriteDTO) {
        if (favoriteRepository.existsByUser_IdAndGame_Id(favoriteDTO.getUserId(), favoriteDTO.getGameId())) {
            return; // Ya existe, evitar duplicado
        }

        User user = userRepository.findById(favoriteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Game game = gameRepository.findById(favoriteDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        Favorite favorite = FavoriteMapper.toEntity(favoriteDTO, user, game);
        favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(UUID userId, UUID gameId) {
        favoriteRepository.deleteByUser_IdAndGame_Id(userId, gameId);
    }

    @Override
public List<FavoriteDTO> getFavorites(UUID userId) {
    List<Favorite> favorites = favoriteRepository.findByUser_Id(userId);

    List<FavoriteDTO> dtos = new ArrayList<>();

    for (Favorite favorite : favorites) {
        Game game = favorite.getGame();

        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(userId);
        dto.setGameId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDeveloper(game.getDeveloper());
        dto.setCoverImage(game.getCoverImage());
        dto.setRating(game.getRating());
        dto.setCategories(game.getCategories());
        dto.setPlatforms(game.getPlatforms());
        dto.setPrice(game.getPrice());

        dtos.add(dto);
    }

    return dtos;
}

}
