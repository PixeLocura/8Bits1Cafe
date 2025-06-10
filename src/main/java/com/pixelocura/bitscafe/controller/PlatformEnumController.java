package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.enums.Platform;
import com.pixelocura.bitscafe.service.PlatformEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/platforms")
public class PlatformEnumController {

    private final PlatformEnumService platformEnumService;

    @GetMapping
    public ResponseEntity<List<Platform>> getAllPlatforms() {
        return ResponseEntity.ok(platformEnumService.getAllPlatforms());
    }

    @GetMapping("/names")
    public ResponseEntity<Map<String, String>> getPlatformsWithDisplayNames() {
        return ResponseEntity.ok(platformEnumService.getPlatformsWithDisplayNames());
    }
}
