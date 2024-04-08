package service;

import model.Pessoa;
import utility.TipoCargo;
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
    public static List<Pessoa> buscarTodasAsPessoas(TipoCargo cargo) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().listarPessoasAptas(cargo);
    }

    public static List<Pessoa> buscarTodasAsPessoas() {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().getPessoas();
    }
}
