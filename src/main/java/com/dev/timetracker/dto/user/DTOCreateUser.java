package com.dev.timetracker.dto.user;

import com.dev.timetracker.utility.category.Plan;
import com.dev.timetracker.utility.category.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record DTOCreateUser(

        Long id,
        @NotBlank
        String username,
        @NotBlank
        String name,
        @CPF
        String cpf,
        @NotNull
        Plan plan,
        @NotNull
        Role role) {}
