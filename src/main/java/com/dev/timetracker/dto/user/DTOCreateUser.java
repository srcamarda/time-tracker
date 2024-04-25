package com.dev.timetracker.dto.user;

import com.dev.timetracker.utility.category.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record DTOCreateUser(

        Long id,
        @NotBlank
        String username,
        @NotBlank
        String name,
        @Email
        String email,
        @CPF
        String cpf,
        @NotNull
        Role role,
        @NotNull
        String addr_zip,
        String addr_country,
        String addr_state,
        String addr_city,
        String addr_street,
        Integer addr_number) {
}