package com.dev.timetracker.dto;

import com.dev.timetracker.model.Tag;

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
