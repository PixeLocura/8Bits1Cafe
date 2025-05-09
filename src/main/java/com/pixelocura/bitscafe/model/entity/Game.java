package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "developer_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_game_developer")
    )
    private Developer developer;

    @Size(min = 1, max = 200)
    @Column(nullable = false)
    private String title;

    @Size(min = 10, max = 3000)
    @Column(nullable = false)
    private String description;

    @Min(0)
    @Column(nullable = false)
    private Double price;

    @URL
    @Column(name = "cover_url", nullable = false)
    private String coverUrl;

    @OneToMany(mappedBy = "game")
    private List<Review> reviews;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "release_date")
    private ZonedDateTime releaseDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
