package com.pixelocura.bitscafe.model.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Language {
    EN("Inglés", "English"),
    ES("Español", "Español"),
    FR("Francés", "Français"),
    DE("Alemán", "Deutsch"),
    IT("Italiano", "Italiano"),
    PT("Portugués", "Português"),
    RU("Ruso", "Русский"),
    JA("Japonés", "日本語"),
    ZH("Chino", "中文"),
    KO("Coreano", "한국어");

    private final String name;
    private final String localName;

    Language(String name, String localName) {
        this.name = name;
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public String getLocalName() {
        return localName;
    }

    public static Optional<Language> fromIsoCode(String isoCode) {
        return Arrays.stream(values())
                .filter(language -> language.name().toLowerCase().equalsIgnoreCase(isoCode.toLowerCase()))
                .findFirst();
    }

    public static Optional<Language> fromName(String name) {
        return Arrays.stream(values())
                .filter(language -> language.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()))
                .findFirst();
    }

    public static Optional<Language> fromLocalName(String localName) {
        return Arrays.stream(values())
                .filter(language -> language.getLocalName().toLowerCase().equalsIgnoreCase(localName.toLowerCase()))
                .findFirst();
    }
}
