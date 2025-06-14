package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.enums.Platform;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlatformEnumService {
    public List<Platform> getAllPlatforms() {
        return Arrays.asList(Platform.values());
    }

    public Map<String, String> getPlatformsWithDisplayNames() {
        return Arrays.stream(Platform.values())
                .collect(Collectors.toMap(
                        Platform::name,
                        Platform::getDisplayName
                ));
    }

    public Platform getPlatformByName(String name) {
        try {
            return Platform.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Platform getPlatformByDisplayName(String displayName) {
        return Arrays.stream(Platform.values())
                .filter(platform -> platform.getDisplayName().equalsIgnoreCase(displayName))
                .findFirst()
                .orElse(null);
    }
}
