package repository;

import dto.PessoaDTO;
import dto.TarefaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tag;
import model.Tarefa;
import utility.TipoPlano;
import utility.converter.ConverterPessoaImp;
import utility.converter.ConverterTarefaImp;
import utility.singleton.PessoaSingleton;
import utility.singleton.TarefaSingleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositoryProjeto {
    ArquivoUtil arquivo;
    List<Projeto> projetos;

    public RepositoryProjeto(ArquivoPaths path) {
        arquivo = new ArquivoUtil(path);
        projetos = carregarProjetos();
    }

    public List<Projeto> carregarProjetos() {
        List<String> projetosStr = arquivo.lerArquivo();
        List<Projeto> projetos = new ArrayList<>();
        projetosStr.stream().skip(1).map((this::projetoParser)).forEach(projetos::add);
        return projetos;
    }

    public void salvarProjetos(List<Projeto> Projetos) {
        Projetos.forEach(this::salvarProjeto);
    }

    public void salvarProjeto(Projeto Projeto) {
        String ProjetoStr = Projeto.getId() + ";"
                + Projeto.getTitulo() + ";"
                + Projeto.getDescricao() + ";"
                + Projeto.getDataHoraInicio() + ";"
                + Projeto.getDataHoraFim();

        arquivo.escreverArquivo(ProjetoStr);
    }

    public Projeto projetoParser(String linha) {
        String[] valores = linha.split(";");
        String id_projeto = valores[0];

        List<Pessoa> pessoasProjeto = buscarPessoas(id_projeto);
        List<Tag> tagsProjeto = buscarTag(id_projeto);
        List<Tarefa> tarefasProjeto = buscarTarefas(id_projeto);

        ConverterPessoaImp converterPessoaImp = new ConverterPessoaImp();
        List<PessoaDTO> pessoasDTO = pessoasProjeto.stream().map(converterPessoaImp::converterParaDTO).toList();

        ConverterTarefaImp converterTarefaImp = new ConverterTarefaImp();
        List<TarefaDTO> tarefasDTO = tarefasProjeto.stream().map(converterTarefaImp::converterParaDTO).toList();

        return new Projeto.Builder()
                .id(id_projeto)
                .titulo(valores[1])
                .descricao(valores[2])
                .dataHoraInicio(LocalDateTime.parse(valores[3]))
                .dataHoraFim(LocalDateTime.parse(valores[4]))
                .pessoasDTO(pessoasDTO)
                .tags(tagsProjeto)
                .tarefasDTO(tarefasDTO)
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

    public Projeto buscarProjetoTitulo(String titulo){
        for (Projeto projeto : projetos){
            if (projeto.getTitulo().toLowerCase().contains(titulo)){
                return projeto;
            }
        }
        return null;
    }

    public List<Pessoa> buscarPessoas(String id) {
        ArquivoUtil arquivoPessoasProjeto = new ArquivoUtil(ArquivoPaths.PESSOAS_PROJ);
        List<String> pessoasStr = arquivoPessoasProjeto.lerArquivo();
        List<Pessoa> pessoasProjeto = new ArrayList<>();

        pessoasStr.stream()
                .filter(linha -> linha.split(";")[1].equals(id))
                .forEach(pessoa -> pessoasProjeto.add(
                        PessoaSingleton
                                .INSTANCE
                                .getRepositoryPessoa()
                                .buscarPessoa(pessoa.split(";")[0])));

        return pessoasProjeto;
    }

    public List<Tag> buscarTag(String id) {
        ArquivoUtil arquivoTagProjeto = new ArquivoUtil(ArquivoPaths.TAGS_PROJ);
        List<String> tagsStr = arquivoTagProjeto.lerArquivo();
        List<Tag> tagsProjeto = new ArrayList<>();

        tagsStr.stream()
                .filter(linha -> linha.split(";")[1].equals(id))
                .forEach(tag -> tagsProjeto.add(Tag.valueOf(tag.split(";")[0])));

        return tagsProjeto;
    }

    public List<Tarefa> buscarTarefas(String id) {
        ArquivoUtil arquivoTarefasProjeto = new ArquivoUtil(ArquivoPaths.TAREFAS_PROJ);
        List<String> tarefasStr = arquivoTarefasProjeto.lerArquivo();
        List<Tarefa> tarefasProjeto = new ArrayList<>();

        tarefasStr.stream().filter(linha -> linha.split(";")[1].equals(id))
                .forEach(tarefa -> tarefasProjeto.add(
                        TarefaSingleton
                                .INSTANCE
                                .getRepositoryTarefa()
                                .buscarTarefa(tarefa.split(";")[0])));

        return tarefasProjeto;
    }
}