package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.GameDTO;
import com.pixelocura.bitscafe.model.entity.Game;
import com.pixelocura.bitscafe.service.AdminGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
public class AdminGameController {
    private final AdminGameService adminGameService;

    @GetMapping
    public ResponseEntity<List<GameDTO>> list() {
        List<GameDTO> games = adminGameService.findAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<GameDTO>> paginate(@PageableDefault(size = 5, sort = "title") Pageable pageable) {
        Page<GameDTO> games = adminGameService.paginate(pageable);
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<GameDTO> create(@RequestBody GameDTO gameDTO) {
        GameDTO createdGame = adminGameService.create(gameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getById(@PathVariable UUID id) {
        GameDTO game = adminGameService.findById(id);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> update(@PathVariable UUID id, @RequestBody GameDTO game) {
        GameDTO updatedGame = adminGameService.update(id, game);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        adminGameService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
