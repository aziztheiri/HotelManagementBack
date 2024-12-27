package com.aziz.booksocialnetwork.dto;

import com.aziz.booksocialnetwork.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
    private String image;
}
