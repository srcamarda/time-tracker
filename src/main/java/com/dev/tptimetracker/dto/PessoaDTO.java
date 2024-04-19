package com.dev.tptimetracker.dto;

import com.dev.tptimetracker.utility.TipoCargo;
import com.dev.tptimetracker.utility.TipoPlano;

import java.util.UUID;

public record PessoaDTO (UUID id,
                         String username,
                         String nome,
                         TipoPlano plano,
                         TipoCargo cargo) {}
