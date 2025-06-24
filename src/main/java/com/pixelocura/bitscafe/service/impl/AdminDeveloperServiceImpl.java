package com.pixelocura.bitscafe.service.impl;

import com.pixelocura.bitscafe.dto.DeveloperDTO;
import com.pixelocura.bitscafe.mapper.DeveloperMapper;
import com.pixelocura.bitscafe.model.entity.Developer;
import com.pixelocura.bitscafe.repository.DeveloperRepository;
import com.pixelocura.bitscafe.repository.GameRepository;
import com.pixelocura.bitscafe.service.AdminDeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminDeveloperServiceImpl implements AdminDeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;
    private final GameRepository gameRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DeveloperDTO> findAll() {
        return developerRepository.findAll().stream()
                .map(developerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeveloperDTO> paginate(Pageable pageable) {
        return developerRepository.findAll(pageable)
                .map(developerMapper::toDTO);
    }

    @Override
    @Transactional
    public DeveloperDTO create(DeveloperDTO developerDTO) {
        // Check for duplicate name
        developerRepository.findByName(developerDTO.getName())
                .ifPresent(developer -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Developer already exists with name: " + developerDTO.getName());
                });

        Developer developer = developerMapper.toEntity(developerDTO);
        Developer savedDeveloper = developerRepository.save(developer);
        return developerMapper.toDTO(savedDeveloper);
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperDTO findById(UUID id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Developer not found with id: " + id));
        // Fetch games and set to developer
        developer.setGames(gameRepository.findByDeveloperId(id));
        return developerMapper.toDTO(developer);
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperDTO findByName(String name) {
        Developer developer = developerRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Developer not found with name: " + name));
        return developerMapper.toDTO(developer);
    }

    @Override
    @Transactional
    public DeveloperDTO update(UUID id, DeveloperDTO developerDTO) {
        Developer existingDeveloper = developerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Developer not found with id: " + id));

        developerRepository.findByName(developerDTO.getName())
                .ifPresent(developer -> {
                    if (!developer.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Developer name already taken: " + developerDTO.getName());
                    }
                });

        developerMapper.updateEntity(developerDTO, existingDeveloper);
        Developer updatedDeveloper = developerRepository.save(existingDeveloper);
        return developerMapper.toDTO(updatedDeveloper);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!developerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Developer not found with id: " + id);
        }
        developerRepository.deleteById(id);
    }
}
