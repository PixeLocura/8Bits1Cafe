package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.model.entity.Favorite;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.service.AdminFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminFavoriteServiceImpl implements AdminFavoriteService {
    @Override
    public List<Favorite> findAll() {
        return List.of();
    }

    @Override
    public Page<Favorite> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public Favorite create(Favorite favorite) {
        return null;
    }

    @Override
    public Favorite findById(User user, Game game) {
        return null;
    }

    @Override
    public Favorite update(User user, Game game, Favorite updatedFavorite) {
        return null;
    }

    @Override
    public void delete(User user, Game game) {

    }

    @Override
    public List<Favorite> findByUser(User user) {
        return List.of();
    }

    @Override
    public List<Favorite> findByGame(Game game) {
        return List.of();
    }
}
