package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String name;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String lastname;

    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    @Column(nullable = false, unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(
            name = "country_iso",
            referencedColumnName = "iso_code",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_country")
    )
    private Country country;

    @ManyToOne
    @JoinColumn(
            name = "developer_profile",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_user_developer_profile")
    )
    private Developer developerProfile;

    @Column(name = "registration_date", nullable = false)
    private ZonedDateTime registrationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.registrationDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
