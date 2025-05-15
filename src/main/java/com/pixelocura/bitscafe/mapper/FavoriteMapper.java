package com.pixelocura.bitscafe.mapper;

import com.pixelocura.bitscafe.dto.FavoriteDTO;
import com.pixelocura.bitscafe.model.entity.Favorite;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;

public class FavoriteMapper {

    public static FavoriteDTO toDTO(Favorite favorite) {
        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(favorite.getUser().getId());
        dto.setGameId(favorite.getGame().getId());
        return dto;
    }

    public static Favorite toEntity(FavoriteDTO dto, User user, Game game) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setGame(game);
        // creationDate se asigna en prePersist
        return favorite;
    }
}
