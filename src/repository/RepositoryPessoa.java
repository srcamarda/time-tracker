package repository;

import model.Pessoa;
import repository.arquivo.ArquivoPaths;
import repository.arquivo.ArquivoUtil;
import utility.ValidadoresEntrada;
import utility.TipoCargo;
import utility.TipoPlano;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum RepositoryPessoa {
    INSTANCE;

    final ArquivoUtil arquivo;
    final List<Pessoa> pessoas;

    RepositoryPessoa() {
        arquivo = new ArquivoUtil(ArquivoPaths.PESSOAS);
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
        pessoas.add(pessoa);
    }

    public Pessoa pessoaParser(String linha) {
        String[] valores = linha.split(";");

        try {
            String id = ValidadoresEntrada.obterUUIDValidado(valores[0]);
            String nome = ValidadoresEntrada.obterNomeValidado(valores[2]);
            String username = ValidadoresEntrada.obterUsernameValidado(valores[1]);
            String cpf = ValidadoresEntrada.obterCpfValidado(valores[3]);
            TipoCargo cargo = ValidadoresEntrada.obterCargoValidado(valores[4]);
            TipoPlano plano = ValidadoresEntrada.obterPlanoValidado(valores[5]);

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

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public Pessoa buscarPessoa(String id) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public Optional<List<Pessoa>> buscarPessoas(Predicate<Pessoa> predicate) {
        return Optional.of(pessoas.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }
}