package service;


import dto.PessoaDTO;
import model.Tarefa;
import utility.singleton.TarefaSingleton;

import java.util.List;
import java.util.UUID;

public class TarefaService {
    public static List<Tarefa> buscarTarefa(String titulo) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasComTitulo(titulo.toLowerCase());
    }
    public static List<Tarefa> buscarTarefa(UUID pessoaId) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasPorPessoa(pessoaId);
    }
    public static List<Tarefa> buscarTarefa(PessoaDTO pessoaDTO) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasPorPessoa(pessoaDTO);
    }
}
