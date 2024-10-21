package com.crezam.lms.repository;

import com.crezam.lms.entity.AppRole;
import com.crezam.lms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(AppRole appRole);
}
