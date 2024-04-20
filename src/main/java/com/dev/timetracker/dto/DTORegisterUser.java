package com.dev.timetracker.dto;

import com.dev.timetracker.utility.category.Plan;
import com.dev.timetracker.utility.category.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTORegisterUser(

        Long id,
        @NotBlank
        String username,
        @NotBlank
        String name,
        @Pattern(regexp = "\\d{11}")
        String cpf,
        @NotNull
        Plan plan,
        @NotNull
        Role role) {}
