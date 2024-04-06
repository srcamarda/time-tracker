package repository;

import model.Pessoa;
import utility.TipoCargo;
import utility.TipoPlano;

import java.util.ArrayList;
import java.util.List;

public class RepositoryPessoa {
    ArquivoUtil arquivo;
    List<Pessoa> pessoas;

    public RepositoryPessoa(ArquivoPaths path) {
        arquivo = new ArquivoUtil(path);
        pessoas = carregarPessoas();
    }

    public List<Pessoa> carregarPessoas() {
        List<String> pessoasStr = arquivo.lerArquivo();
        List<Pessoa> pessoas = new ArrayList<>();
        pessoasStr.stream().skip(1).map((this::pessoaParser)).forEach(pessoas::add);
        return pessoas;
    }

    public void salvarPessoas(List<Pessoa> pessoas) {
        pessoas.forEach(this::salvarPessoa);
    }

    public void salvarPessoa(Pessoa pessoa) {
        String pessoaStr = pessoa.getId() + ";"
                + pessoa.getNome() + ";"
                + pessoa.getCpf() + ";"
                + pessoa.getCargo().toString() + ";"
                + pessoa.getPlano().toString();

        arquivo.escreverArquivo(pessoaStr);
    }

    public Pessoa pessoaParser(String linha) {
        String[] valores = linha.split(";");

        return new Pessoa.Builder()
                .id(valores[0])
                .nome(valores[1])
                .cpf(valores[2])
                .cargo(TipoCargo.valueOf(valores[3]))
                .plano(TipoPlano.valueOf(valores[4]))
                .build();
    }

    public Pessoa buscarPessoa(String id) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getId().toString().equals(id)) {
                return pessoa;
            }
        }
        return null;
    }
}