package com.aziz.booksocialnetwork.repositories;

import com.aziz.booksocialnetwork.JWT.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    Optional<Token> findByToken(String token);
}
