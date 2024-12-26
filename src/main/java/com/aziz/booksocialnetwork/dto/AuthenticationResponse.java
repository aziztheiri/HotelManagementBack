package com.aziz.booksocialnetwork.dto;

import com.aziz.booksocialnetwork.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}
