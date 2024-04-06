package repository;

import model.Pessoa;
import model.Projeto;
import utility.TipoPlano;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class RepositoryProjeto {
    ArquivoUtil arquivo;
    HashSet<Projeto> projetos;
    RepositoryPessoa repositoryPessoa;
    RepositoryTarefa repositoryTarefa;

    public RepositoryProjeto(ArquivoPaths path, RepositoryPessoa repositoryPessoa, RepositoryTarefa repositoryTarefa) {
        arquivo = new ArquivoUtil(path);
        projetos = carregarProjetos();
        this.repositoryPessoa = repositoryPessoa;
        this.repositoryTarefa = repositoryTarefa;
    }

    public HashSet<Projeto> carregarProjetos() {
        List<String> projetosStr = arquivo.lerArquivo();
        HashSet<Projeto> projetos = new HashSet<>();
        projetosStr.stream().skip(1).map((this::projetoParser)).forEach(projetos::add);
        return projetos;
    }

    public void salvarProjetos(List<Projeto> Projetos) {
        Projetos.forEach(this::salvarProjeto);
    }

    public void salvarProjeto(Projeto Projeto) {
        String ProjetoStr = Projeto.getId() + ";" + Projeto.getTitulo() + ";" + Projeto.getDescricao() + ";" + Projeto.getPlano() + ";" + Projeto.getDataHoraInicio() + ";" + Projeto.getDataHoraFim();
        arquivo.escreverArquivo(ProjetoStr);
    }

    public Projeto projetoParser(String linha) {
        String[] valores = linha.split(";");

        return new Projeto.Builder()
                .id(valores[0])
                .titulo(valores[1])
                .descricao(valores[2])
                .plano(TipoPlano.valueOf(valores[3]))
                .dataHoraInicio(LocalDateTime.parse(valores[4]))
                .dataHoraFim(LocalDateTime.parse(valores[5]))
                .build();

    }

    public Projeto buscarProjeto(String id) {
        for (Projeto projeto : projetos) {
            if (projeto.getId().toString().equals(id)) {
                return projeto;
            }
        }
        return null;
    }
}