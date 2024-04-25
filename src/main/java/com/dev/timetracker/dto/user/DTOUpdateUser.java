package com.dev.timetracker.dto.user;

import com.dev.timetracker.utility.category.Plan;
import com.dev.timetracker.utility.category.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOUpdateUser(

        @NotNull
        Long id,
        String username,
        String name,
        Plan plan,
        Role role) {}
