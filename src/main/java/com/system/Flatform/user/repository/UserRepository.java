package com.system.Flatform.user.repository;

import com.system.Flatform.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail (String userEmail);
}
