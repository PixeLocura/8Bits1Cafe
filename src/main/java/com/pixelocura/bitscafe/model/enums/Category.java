package com.pixelocura.bitscafe.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Category {
    ACTION("Acción", "Juegos rápidos centrados en el combate y el movimiento."),
    ADVENTURE("Aventura", "Juegos de exploración impulsados por la historia."),
    RPG("RPG", "Juegos de rol con desarrollo de personajes."),
    STRATEGY("Estrategia", "Juegos centrados en la toma de decisiones tácticas."),
    SIMULATION("Simulación", "Juegos que simulan actividades del mundo real."),
    SPORTS("Deportes", "Juegos competitivos basados en deportes."),
    RACING("Carreras", "Juegos de carreras de vehículos."),
    PUZZLE("Puzzle", "Juegos centrados en la resolución de problemas."),
    PLATFORMER("Plataformas", "Juegos que implican el movimiento de personajes a través de plataformas."),
    SHOOTER("Shooter", "Juegos de combate centrados en el uso de armas."),
    HORROR("Horror", "Juegos diseñados para crear miedo y tensión."),
    CASUAL("Casual", "Juegos simples y fáciles de jugar."),
    FANTASY("Fantasía", "Juegos ambientados en mundos mágicos o míticos."),
    ARCADE("Arcade", "Juegos rápidos que suelen encontrarse en arcades."),
    ROGUELIKE("Roguelike", "Juegos con niveles generados proceduralmente y muerte permanente.");

    private final String displayName;
    private final String description;

    Category(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public static Optional<Category> fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(category -> category.getDisplayName().equalsIgnoreCase(displayName))
                .findFirst();
    }
}
