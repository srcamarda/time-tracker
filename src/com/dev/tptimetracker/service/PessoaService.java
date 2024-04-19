package com.dev.tptimetracker.service;

import com.dev.tptimetracker.model.Pessoa;
import com.dev.tptimetracker.repository.RepositoryPessoa;
import com.dev.tptimetracker.utility.TipoCargo;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class PessoaService {
    public static void criarPessoa(Pessoa pessoa) {
        RepositoryPessoa.INSTANCE.salvarPessoa(pessoa);
    }

    public static Pessoa buscarPessoa(String username) {
        Predicate<Pessoa> pessoaPredicate = p -> p.getUsername().trim().equalsIgnoreCase(username);
        return RepositoryPessoa.INSTANCE.buscarPessoas(pessoaPredicate).orElseThrow().get(0);
    }

    public static Pessoa buscarPessoa(UUID uuid) {
        Predicate<Pessoa> pessoaPredicate = p -> p.getId().equals(uuid);
        return RepositoryPessoa.INSTANCE.buscarPessoas(pessoaPredicate).orElseThrow().get(0);
    }

    public static List<Pessoa> buscarPessoas(String nome) {
        Predicate<Pessoa> pessoaPredicate = p -> p.getNome().trim().toLowerCase().contains(nome.toLowerCase());
        return RepositoryPessoa.INSTANCE.buscarPessoas(pessoaPredicate).orElseThrow();
    }

    public static List<Pessoa> buscarPessoas(TipoCargo cargo) {
        Predicate<Pessoa> pessoaPredicate = p -> p.getCargo().equals(cargo);
        return RepositoryPessoa.INSTANCE.buscarPessoas(pessoaPredicate).orElseThrow();
    }
}
