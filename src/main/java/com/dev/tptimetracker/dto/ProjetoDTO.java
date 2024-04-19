package com.dev.tptimetracker.dto;

import com.dev.tptimetracker.model.Pessoa;
import com.dev.tptimetracker.model.Tag;
import com.dev.tptimetracker.model.Tarefa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjetoDTO(UUID id,
                         String titulo,
                         String descricao,
                         List<PessoaDTO> pessoas,
                         LocalDateTime dataHoraInicio,
                         LocalDateTime dataHoraFim,
                         List<Tag> tags,
                         List<TarefaDTO> tarefas) { }
