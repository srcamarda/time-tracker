package com.dev.tptimetracker.dto;

import com.dev.tptimetracker.model.Pessoa;
import com.dev.tptimetracker.model.Tag;

import java.time.LocalDateTime;
import java.util.UUID;

public record TarefaDTO (UUID id,
                         String titulo,
                         String descricao,
                         PessoaDTO pessoa,
                         LocalDateTime dataHoraInicio,
                         LocalDateTime dataHoraFim,
                         Tag tag){
}
