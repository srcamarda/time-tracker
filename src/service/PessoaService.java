package service;

import dto.PessoaDTO;
import model.Pessoa;
import model.Tarefa;
import utility.singleton.PessoaSingleton;
import utility.singleton.TarefaSingleton;

import java.util.List;
import java.util.UUID;

public class PessoaService {
    public void criarPessoa(){}

    public static Pessoa buscarPessoa(String username) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoaPorUsername(username.trim().toLowerCase());
    }
    public static List<Pessoa> buscarPessoas(String nome) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoas(nome.trim().toLowerCase());
    }
}
