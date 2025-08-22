package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Role;
import com.staj.biletbul.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
    boolean existsByRoleName(RoleName roleName);
}
