package com.pixelocura.bitscafe.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Getter
public enum Country {
    US("Estados Unidos", "United States", "https://flagcdn.com/us.svg"),
    ES("España", "España", "https://flagcdn.com/es.svg"),
    MX("México", "México", "https://flagcdn.com/mx.svg"),
    AR("Argentina", "Argentina", "https://flagcdn.com/ar.svg"),
    BR("Brasil", "Brasil", "https://flagcdn.com/br.svg"),
    CL("Chile", "Chile", "https://flagcdn.com/cl.svg"),
    CO("Colombia", "Colombia", "https://flagcdn.com/co.svg"),
    PE("Perú", "Perú", "https://flagcdn.com/pe.svg"),
    UY("Uruguay", "Uruguay", "https://flagcdn.com/uy.svg");

    private final String name;
    private final String localName;
    private final String flagUrl;

    Country(String name, String localName, String flagUrl) {
        this.name = name;
        this.localName = localName;
        this.flagUrl = flagUrl;
    }

    public static Optional<Country> fromIsoCode(String isoCode) {
        return Arrays.stream(values())
                .filter(country -> country.name().equalsIgnoreCase(isoCode))
                .findFirst();
    }

    public static Optional<Country> fromName(String name) {
        return Arrays.stream(values())
                .filter(country -> country.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public static Optional<Country> fromLocalName(String localName) {
        return Arrays.stream(values())
                .filter(country -> country.getLocalName().equalsIgnoreCase(localName))
                .findFirst();
    }

    public Map<String, String> toDetailsMap() {
        return Map.of(
                "isoCode", this.name(),
                "name", this.getName(),
                "localName", this.getLocalName(),
                "flagUrl", this.getFlagUrl());
    }
}
