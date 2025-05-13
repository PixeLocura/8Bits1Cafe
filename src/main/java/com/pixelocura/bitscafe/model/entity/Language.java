package com.pixelocura.bitscafe.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "languages")
public class Language {
    @Id
    @Size(min = 2, max = 3)
    @Pattern(regexp = "^[A-Z]{2,3}$")
    @Column(name = "iso_code", nullable = false)
    private String isoCode;

    @Column(nullable = false)
    private String name;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

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
