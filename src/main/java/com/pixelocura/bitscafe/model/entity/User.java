package com.pixelocura.bitscafe.model.entity;

import com.pixelocura.bitscafe.model.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.pixelocura.bitscafe.model.enums.Country;

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

    @Column(name = "password_hash", nullable = true)
    private String passwordHash;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String lastname;

    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "country_iso", nullable = false)
    private Country country;

    @OneToOne
    @JoinColumn(name = "developer_profile", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_developer_profile"))
    private Developer developerProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_role"))
    private Role role;

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

    public ERole getRoleName() {
        return role != null ? role.getName() : null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                // Avoid recursion: only print developerProfile id
                ", developerProfileId=" + (developerProfile != null ? developerProfile.getId() : null) +
                ", role=" + (role != null ? role.getName() : null) +
                '}';
    }

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

}
