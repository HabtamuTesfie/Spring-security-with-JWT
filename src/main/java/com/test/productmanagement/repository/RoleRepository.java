package com.test.productmanagement.repository;


import com.test.productmanagement.model.Role;
import com.test.productmanagement.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
