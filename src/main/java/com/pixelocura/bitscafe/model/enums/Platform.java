package com.pixelocura.bitscafe.model.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Platform {
    WINDOWS("Windows"),
    MAC_OS("MacOS"),
    LINUX("Linux"),
    ANDROID("Android"),
    IOS("iOS");

    private final String displayName;

    Platform(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Optional<Platform> fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(platform -> platform.getDisplayName().equalsIgnoreCase(displayName))
                .findFirst();
    }
}
