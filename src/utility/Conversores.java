package utility;

import dto.PessoaDTO;
import dto.ProjetoDTO;
import dto.TarefaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tarefa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Conversores {

    public static Pessoa converterParaModel(PessoaDTO pessoaDTO) {
        return new Pessoa.Builder()
                .id(pessoaDTO.id().toString())
                .username(pessoaDTO.username())
                .nome(pessoaDTO.nome())
                .plano(pessoaDTO.plano())
                .cargo(pessoaDTO.cargo())
                .build();
    }
    public static Projeto converterParaModel(ProjetoDTO projetoDTO) {
        return new Projeto.Builder()
                .id(projetoDTO.id().toString())
                .titulo(projetoDTO.titulo())
                .descricao(projetoDTO.descricao())
                .pessoasDTO(projetoDTO.pessoas())
                .dataHoraInicio(projetoDTO.dataHoraInicio())
                .dataHoraFim(projetoDTO.dataHoraFim())
                .tags(projetoDTO.tags())
                .tarefasDTO(projetoDTO.tarefas())
                .build();
    }
    public static Tarefa converterParaModel(TarefaDTO tarefa) {
        return new Tarefa.Builder().id(tarefa.id().toString())
                .titulo(tarefa.titulo())
                .descricao(tarefa.descricao())
                .pessoaDTO(tarefa.pessoa())
                .dataHoraInicio(tarefa.dataHoraInicio())
                .dataHoraFim(tarefa.dataHoraFim())
                .tag(tarefa.tag())
                .build();
    }
    public static PessoaDTO converterParaDTO(Pessoa pessoa) {
        return new PessoaDTO(
                pessoa.getId(),
                pessoa.getUsername(),
                pessoa.getNome(),
                pessoa.getPlano(),
                pessoa.getCargo());
    }
    public static ProjetoDTO converterParaDTO(Projeto projeto) {
        return  new ProjetoDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getPessoasDTO(),
                projeto.getDataHoraInicio(),
                projeto.getDataHoraFim(),
                projeto.getTags(),
                projeto.getTarefasDTO());
    }
    public static TarefaDTO converterParaDTO(Tarefa tarefa) {
        return new TarefaDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPessoaDTO(),
                tarefa.getDataHoraInicio(),
                tarefa.getDataHoraFim(),
                tarefa.getTag());
    }

    public static LocalDate converterStringParaDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
            return null;
        }
    }
}
