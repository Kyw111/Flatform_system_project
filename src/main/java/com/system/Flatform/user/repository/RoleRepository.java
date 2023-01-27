package com.system.Flatform.user.repository;

import com.system.Flatform.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole (String role);
}
