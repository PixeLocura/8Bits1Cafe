package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_developer_user"))
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private String website;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToMany(mappedBy = "developer")
    private List<Game> games;

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
