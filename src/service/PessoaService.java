package service;

import model.Pessoa;
import utility.singleton.PessoaSingleton;

public class PessoaService {
    public void criarPessoa(){}

    public static Pessoa buscarPessoa(String username) {
        return PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoaPorUsername(username.trim().toLowerCase());
    }
}
