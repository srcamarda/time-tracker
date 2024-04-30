package com.dev.timetracker.dto.user;

import com.dev.timetracker.utility.category.Role;
import com.dev.timetracker.entity.EntityUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOListUser(

        Long id,
        @NotBlank
        String name,
        @Email
        String email,
        @NotNull
        Role role) {

    public DTOListUser(EntityUser user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
}