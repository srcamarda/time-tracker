package com.dev.timetracker.dto.user;

import com.dev.timetracker.utility.category.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DTOUpdateUser(

        @NotNull
        Long id,
        String username,
        String name,
        @Email
        String email,
        Role role,
        String addr_zip,
        String addr_country,
        String addr_state,
        String addr_city,
        String addr_street,
        Integer addr_number) {
}