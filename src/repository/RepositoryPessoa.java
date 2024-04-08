package repository;

import model.Pessoa;
import utility.Entradas;
import utility.TipoCargo;
import utility.TipoPlano;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RepositoryPessoa {
    ArquivoUtil arquivo;
    List<Pessoa> pessoas;

    public List<Pessoa> listarPessoasAptas(Enum cargo) {
        return pessoas.stream().filter(pessoa ->pessoa.getCargo().equals(cargo))
                .collect(Collectors.toList());
    }

    public RepositoryPessoa(ArquivoPaths path) {
        arquivo = new ArquivoUtil(path);
        pessoas = carregarPessoas();
    }

    public List<Pessoa> carregarPessoas() {
        List<String> pessoasStr = arquivo.lerArquivo();
        List<Pessoa> pessoas = new ArrayList<>();
        pessoasStr.stream().skip(1)
                .map((this::pessoaParser))
                .filter(Objects::nonNull)
                .forEach(pessoas::add);

        return pessoas;
    }

    public void salvarPessoas(List<Pessoa> pessoas) {
        pessoas.forEach(this::salvarPessoa);
    }

    public void salvarPessoa(Pessoa pessoa) {
        String pessoaStr = pessoa.getId() + ";"
                + pessoa.getUsername() + ";"
                + pessoa.getNome() + ";"
                + pessoa.getCpf() + ";"
                + pessoa.getCargo().toString() + ";"
                + pessoa.getPlano().toString();

        arquivo.escreverArquivo(pessoaStr);
    }

    public Pessoa pessoaParser(String linha) {
        String[] valores = linha.split(";");

        try {
            String id = Entradas.obterUUIDValidado(valores[0]);
            String nome = Entradas.obterNomeValidado(valores[2]);
            String username = Entradas.obterUsernameValidado(valores[1], nome);
            String cpf = Entradas.obterCpfValidado(valores[3]);
            TipoCargo cargo = Entradas.obterCargoValidado(valores[4]);
            TipoPlano plano = Entradas.obterPlanoValidado(valores[5]);

            return new Pessoa.Builder()
                    .id(id)
                    .username(username)
                    .nome(nome)
                    .cpf(cpf)
                    .cargo(cargo)
                    .plano(plano)
                    .build();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Pessoa buscarPessoa(String id) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public Pessoa buscarPessoaPorUsername(String username) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    public List<Pessoa> buscarPessoas(String nome) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }
}