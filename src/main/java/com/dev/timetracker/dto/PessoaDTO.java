package com.dev.timetracker.dto;

import com.dev.timetracker.utility.TipoCargo;
import com.dev.timetracker.utility.TipoPlano;

import java.util.UUID;

public record PessoaDTO (UUID id,
                         String username,
                         String nome,
                         TipoPlano plano,
                         TipoCargo cargo) {}
