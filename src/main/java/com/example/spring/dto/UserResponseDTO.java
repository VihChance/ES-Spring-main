package com.example.spring.dto;

import com.example.spring.domain.user.UserRole;

public class UserResponseDTO {

    private Long id;
    private String email;
    private UserRole role;

    public UserResponseDTO(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}
