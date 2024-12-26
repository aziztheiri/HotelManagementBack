package com.aziz.booksocialnetwork.services.auth;

import com.aziz.booksocialnetwork.dto.SignUpRequest;
import com.aziz.booksocialnetwork.dto.UserDto;

public interface AuthService {
    public UserDto createUser(SignUpRequest signUpRequest);
}
