package com.aziz.booksocialnetwork.repositories;

import com.aziz.booksocialnetwork.entities.User;
import com.aziz.booksocialnetwork.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String email);
    Optional<User> findByUserRole(UserRole userRole);
}
