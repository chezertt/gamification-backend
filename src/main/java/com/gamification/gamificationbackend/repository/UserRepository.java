package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.User;
import com.gamification.gamificationbackend.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByIdAndRole(Long id, UserRole userRole);
    boolean existsByEmail(String email);
}
