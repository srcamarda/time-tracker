package com.dev.tptimetracker.repository;

import com.dev.tptimetracker.dto.PessoaDTO;
import com.dev.tptimetracker.dto.TarefaDTO;
import com.dev.tptimetracker.model.Pessoa;
import com.dev.tptimetracker.model.Projeto;
import com.dev.tptimetracker.model.Tag;
import com.dev.tptimetracker.model.Tarefa;
import com.dev.tptimetracker.repository.arquivo.ArquivoPaths;
import com.dev.tptimetracker.repository.arquivo.ArquivoUtil;
import com.dev.tptimetracker.utility.Conversores;
import com.dev.tptimetracker.utility.ValidadoresEntrada;
import com.dev.tptimetracker.view.mensagens.MensagensErro;
import com.dev.tptimetracker.utility.Validadores;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum RepositoryProjeto {
    INSTANCE;

    final ArquivoUtil arquivo;
    final List<Projeto> projetos;

    RepositoryProjeto() {
        arquivo = new ArquivoUtil(ArquivoPaths.PROJETOS);
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

    public void salvarPessoasProjeto(UUID idProjeto, List<Pessoa> pessoas) {
        ArquivoUtil arquivoPessoasProjeto = new ArquivoUtil(ArquivoPaths.PESSOAS_PROJ);

        pessoas.forEach(pessoa -> {
            String pessoaStr = pessoa.getId() + ";" + idProjeto.toString();
            arquivoPessoasProjeto.escreverArquivo(pessoaStr);
        });
    }

    public void salvarTarefasProjeto(UUID idProjeto, List<Tarefa> tarefas) {
        ArquivoUtil arquivoTarefasProjeto = new ArquivoUtil(ArquivoPaths.TAREFAS_PROJ);

        tarefas.forEach(tarefa -> {
            String tarefaStr = tarefa.getId() + ";" + idProjeto.toString();
            arquivoTarefasProjeto.escreverArquivo(tarefaStr);
        });
    }

    public void salvarTagProjeto(UUID idProjeto, List<Tag> tags) {
        ArquivoUtil arquivoTagProjeto = new ArquivoUtil(ArquivoPaths.TAGS_PROJ);

        tags.forEach(tag -> {
            String tagStr = idProjeto.toString() + ";" + tag.toString();
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

            salvarPessoasProjeto(projeto.getId(), pessoas);
        }

        if (!Objects.isNull(projeto.getTarefasDTO()) && !projeto.getTarefasDTO().isEmpty()) {
            List<Tarefa> tarefas = projeto.getTarefasDTO()
                    .stream().map(Conversores::converterParaModel)
                    .toList();

            salvarTarefasProjeto(projeto.getId(), tarefas);
        }

        if (!Objects.isNull(projeto.getTags()) && !projeto.getTags().isEmpty()) {
            salvarTagProjeto(projeto.getId(), projeto.getTags());
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
                dataHoraInicio = ValidadoresEntrada.obterDataTimeValidada(valores[3]);
            else
                dataHoraInicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            //Permite carregar projetos em andamento (sem data de término)
            if (valores.length > 4 && !valores[4].isEmpty() && !valores[4].equals("null")) {
                LocalDateTime dataHoraFim = ValidadoresEntrada.obterDataTimeValidada(valores[4]);

                if (!Validadores.validaDataFinal(dataHoraInicio, dataHoraFim)) {
                    System.out.println(MensagensErro.ERRO_DATA_FINAL.getMensagem());
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

    public Optional<List<Projeto>> buscarProjetos(Predicate<Projeto> predicate) {
        return Optional.of(projetos.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }

    public List<Pessoa> buscarPessoasDoProjeto(String id) {
        ArquivoUtil arquivoPessoasProjeto = new ArquivoUtil(ArquivoPaths.PESSOAS_PROJ);
        List<String> pessoasStr = arquivoPessoasProjeto.lerArquivo();
        List<Pessoa> pessoasProjeto = new ArrayList<>();

        pessoasStr.stream()
                .filter(linha -> linha.split(";")[1].equals(id))
                .map(pessoa -> RepositoryPessoa.INSTANCE.buscarPessoa(pessoa.split(";")[0]))
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
                .map(tarefa -> RepositoryTarefa.INSTANCE.buscarTarefa(tarefa.split(";")[0]))
                .filter(Objects::nonNull)
                .forEach(tarefasProjeto::add);

        return tarefasProjeto;
    }
}