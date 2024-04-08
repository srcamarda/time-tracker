package service;

import model.Pessoa;
import utility.singleton.PessoaSingleton;

import java.util.List;

public class PessoaService {
    public void criarPessoa(){}

    public static Pessoa buscarPessoa(String username) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoaPorUsername(username.trim().toLowerCase());
    }
    public static List<Pessoa> buscarPessoas(String nome) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoas(nome.trim().toLowerCase());
    }
    public static List<Pessoa> buscarTodasAsPessoas(Enum cargo) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().listarPessoasAptas(cargo);
    }
}
