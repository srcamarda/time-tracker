package com.dev.timetracker.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record DTOLoginUser(

        @NotBlank
        String username,
        @CPF
        String cpf) {
}