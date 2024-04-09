package service;

import model.Pessoa;
import repository.RepositoryPessoa;
import utility.TipoCargo;

import java.util.List;

public class PessoaService {
    public void criarPessoa(Pessoa pessoa) {
        RepositoryPessoa.INSTANCE.salvarPessoa(pessoa);
    }

    public static Pessoa buscarPessoa(String username) {
        return RepositoryPessoa.INSTANCE.buscarPessoaPorUsername(username.trim().toLowerCase());
    }

    public static List<Pessoa> buscarPessoas(String nome) {
        return RepositoryPessoa.INSTANCE.buscarPessoas(nome.trim().toLowerCase());
    }

    public static List<Pessoa> buscarTodasAsPessoas(TipoCargo cargo) {
        return RepositoryPessoa.INSTANCE.listarPessoasAptas(cargo);
    }

    public static List<Pessoa> buscarTodasAsPessoas() {
        return RepositoryPessoa.INSTANCE.getPessoas();
    }
}
