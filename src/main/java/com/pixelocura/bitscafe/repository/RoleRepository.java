package com.pixelocura.bitscafe.repository;


import com.pixelocura.bitscafe.model.entity.Role;
import com.pixelocura.bitscafe.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    //Buscar un rol por su nombre (usando el enum)
    Optional<Role> findByName(ERole name);
}

