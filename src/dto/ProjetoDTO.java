package dto;

import model.Pessoa;
import model.Tag;
import model.Tarefa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjetoDTO(UUID id,
                         String titulo,
                         String descricao,
                         List<Pessoa> pessoas,
                         LocalDateTime dataHoraInicio,
                         LocalDateTime dataHoraFim,
                         List<Tag> tags,
                         List<Tarefa> tarefas) { }
