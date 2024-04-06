package service;

import model.Pessoa;
import utility.singleton.PessoaSingleton;

public class PessoaService {
    public void criarPessoa(){}

    public static Pessoa buscarPessoa(String username) {
        String usernameSearch = username.trim().toLowerCase();

        return PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoaPorUsername(usernameSearch);
    }
}
