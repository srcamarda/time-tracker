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
        String addrZip,
        String addrCountry,
        String addrState,
        String addrCity,
        String addrStreet,
        Integer addrNumber) {
}