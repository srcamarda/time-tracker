package repository;

import dto.PessoaDTO;
import dto.TarefaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tag;
import model.Tarefa;
import utility.Conversores;
import utility.ValidadoresEntrada;
import utility.MensagensEntrada;
import utility.Validadores;
import utility.singleton.PessoaSingleton;
import utility.singleton.TarefaSingleton;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        projetosStr.stream().skip(1)
                .map((this::projetoParser))
                .filter(Objects::nonNull)
                .forEach(projetos::add);

        return projetos;
    }

    public void salvarProjetos(List<Projeto> Projetos) {
        Projetos.forEach(this::salvarProjeto);
    }

    public void salvarPessoasProjeto(String idProjeto, List<Pessoa> pessoas) {
        ArquivoUtil arquivoPessoasProjeto = new ArquivoUtil(ArquivoPaths.PESSOAS_PROJ);

        pessoas.forEach(pessoa -> {
            String pessoaStr = pessoa.getId() + ";" + idProjeto;
            arquivoPessoasProjeto.escreverArquivo(pessoaStr);
        });
    }

    public void salvarTarefasProjeto(String idProjeto, List<Tarefa> tarefas) {
        ArquivoUtil arquivoTarefasProjeto = new ArquivoUtil(ArquivoPaths.TAREFAS_PROJ);

        tarefas.forEach(tarefa -> {
            String tarefaStr = tarefa.getId() + ";" + idProjeto;
            arquivoTarefasProjeto.escreverArquivo(tarefaStr);
        });
    }

    public void salvarTagProjeto(String idProjeto, List<Tag> tags) {
        ArquivoUtil arquivoTagProjeto = new ArquivoUtil(ArquivoPaths.TAGS_PROJ);

        tags.forEach(tag -> {
            String tagStr = idProjeto + ";" + tag.toString();
            arquivoTagProjeto.escreverArquivo(tagStr);
        });
    }

    public void salvarProjeto(Projeto projeto) {
        String ProjetoStr = projeto.getId() + ";"
                + projeto.getTitulo() + ";"
                + projeto.getDescricao() + ";"
                + projeto.getDataHoraInicio() + ";"
                + projeto.getDataHoraFim();

        arquivo.escreverArquivo(ProjetoStr);
        projetos.add(projeto);

        if (!Objects.isNull(projeto.getPessoasDTO()) && !projeto.getPessoasDTO().isEmpty()) {
            List<Pessoa> pessoas = projeto.getPessoasDTO()
                    .stream().map(Conversores::converterParaModel)
                    .toList();

            salvarPessoasProjeto(projeto.getId().toString(), pessoas);
        }

        if (!Objects.isNull(projeto.getTarefasDTO()) && !projeto.getTarefasDTO().isEmpty()) {
            List<Tarefa> tarefas = projeto.getTarefasDTO()
                    .stream().map(Conversores::converterParaModel)
                    .toList();

            salvarTarefasProjeto(projeto.getId().toString(), tarefas);
        }

        if (!Objects.isNull(projeto.getTags()) && !projeto.getTags().isEmpty()) {
            salvarTagProjeto(projeto.getId().toString(), projeto.getTags());
        }
    }

    public Projeto projetoParser(String linha) {
        String[] valores = linha.split(";");

        try {
            String id_projeto = ValidadoresEntrada.obterUUIDValidado(valores[0]);
            String titulo = ValidadoresEntrada.obterTextoValidado(valores[1]);
            String descricao = ValidadoresEntrada.obterTextoValidado(valores[2]);

            List<Pessoa> pessoasProjeto = buscarPessoasDoProjeto(id_projeto);
            List<Tarefa> tarefasProjeto = buscarTarefasDoProjeto(id_projeto);
            List<Tag> tagsProjeto = buscarTag(id_projeto);

            List<PessoaDTO> pessoasDTO = pessoasProjeto
                    .stream().map(Conversores::converterParaDTO)
                    .toList();

            List<TarefaDTO> tarefasDTO = tarefasProjeto
                    .stream().map(Conversores::converterParaDTO)
                    .toList();

            //Caso não tenha data de início, utiliza a atual
            LocalDateTime dataHoraInicio;
            if (!valores[3].isEmpty() && !valores[3].equals("null"))
                dataHoraInicio = ValidadoresEntrada.obterDataValidada(valores[3]);
            else
                dataHoraInicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            //Permite carregar projetos em andamento (sem data de término)
            if (valores.length > 4 && !valores[4].isEmpty() && !valores[4].equals("null")) {
                LocalDateTime dataHoraFim = ValidadoresEntrada.obterDataValidada(valores[4]);

                if (!Validadores.validaDataFinal(dataHoraInicio, dataHoraFim)) {
                    System.out.println(MensagensEntrada.ERRO_DATA_FINAL.getMensagem());
                    return null;
                }

                return new Projeto.Builder()
                        .id(id_projeto)
                        .titulo(titulo)
                        .descricao(descricao)
                        .dataHoraInicio(dataHoraInicio)
                        .dataHoraFim(dataHoraFim)
                        .pessoasDTO(pessoasDTO)
                        .tags(tagsProjeto)
                        .tarefasDTO(tarefasDTO)
                        .build();
            }

            return new Projeto.Builder()
                    .id(id_projeto)
                    .titulo(titulo)
                    .descricao(descricao)
                    .dataHoraInicio(dataHoraInicio)
                    .pessoasDTO(pessoasDTO)
                    .tags(tagsProjeto)
                    .tarefasDTO(tarefasDTO)
                    .build();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Projeto buscarProjeto(String id) {
        return projetos.stream()
                .filter(projeto -> projeto.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public List<Projeto> buscarProjetos(String titulo) {
        return projetos.stream()
                .filter(tarefa -> tarefa.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Pessoa> buscarPessoasDoProjeto(String id) {
        ArquivoUtil arquivoPessoasProjeto = new ArquivoUtil(ArquivoPaths.PESSOAS_PROJ);
        List<String> pessoasStr = arquivoPessoasProjeto.lerArquivo();
        List<Pessoa> pessoasProjeto = new ArrayList<>();

        pessoasStr.stream()
                .filter(linha -> linha.split(";")[1].equals(id))
                .map(pessoa -> PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoa.split(";")[0]))
                .filter(Objects::nonNull)
                .forEach(pessoasProjeto::add);

        return pessoasProjeto;
    }

    public List<Tag> buscarTag(String id) {
        ArquivoUtil arquivoTagProjeto = new ArquivoUtil(ArquivoPaths.TAGS_PROJ);
        List<String> tagsStr = arquivoTagProjeto.lerArquivo();
        List<Tag> tagsProjeto = new ArrayList<>();

        tagsStr.stream()
                .filter(linha -> linha.split(";")[0].equals(id))
                .forEach(tag -> tagsProjeto.add(Tag.valueOf(tag.split(";")[1])));

        return tagsProjeto;
    }

    public List<Tarefa> buscarTarefasDoProjeto(String id) {
        ArquivoUtil arquivoTarefasProjeto = new ArquivoUtil(ArquivoPaths.TAREFAS_PROJ);
        List<String> tarefasStr = arquivoTarefasProjeto.lerArquivo();
        List<Tarefa> tarefasProjeto = new ArrayList<>();

        tarefasStr.stream().filter(linha -> linha.split(";")[1].equals(id))
                .map(tarefa -> TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefa(tarefa.split(";")[0]))
                .filter(Objects::nonNull)
                .forEach(tarefasProjeto::add);

        return tarefasProjeto;
    }
}