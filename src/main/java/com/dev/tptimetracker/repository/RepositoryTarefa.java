package com.dev.tptimetracker.repository;

import com.dev.tptimetracker.dto.PessoaDTO;
import com.dev.tptimetracker.model.Pessoa;
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

public enum RepositoryTarefa {
    INSTANCE;

    final ArquivoUtil arquivo;
    final List<Tarefa> tarefas;

    RepositoryTarefa() {
        arquivo = new ArquivoUtil(ArquivoPaths.TAREFAS);
        tarefas = carregarTarefas();
    }

    public List<Tarefa> carregarTarefas() {
        List<String> tarefasStr = arquivo.lerArquivo();
        List<Tarefa> tarefas = new ArrayList<>();
        tarefasStr.stream().skip(1)
                .map((this::tarefaParser))
                .filter(Objects::nonNull)
                .forEach(tarefas::add);

        return tarefas;
    }

    public void salvarTarefas(List<Tarefa> tarefas) {
        tarefas.forEach(this::salvarTarefa);
    }

    public void salvarTarefa(Tarefa Tarefa) {
        String tarefaStr = Tarefa.getId() + ";"
                + Tarefa.getTitulo() + ";"
                + Tarefa.getDescricao() + ";"
                + Tarefa.getTag() + ";"
                + Tarefa.getPessoaDTO().id() + ";"
                + Tarefa.getDataHoraInicio() + ";"
                + Tarefa.getDataHoraFim();

        arquivo.escreverArquivo(tarefaStr);
        tarefas.add(Tarefa);
    }

    public Tarefa tarefaParser(String linha) {
        String[] valores = linha.split(";");

        try {
            String id = ValidadoresEntrada.obterUUIDValidado(valores[0]);
            String titulo = ValidadoresEntrada.obterTextoValidado(valores[1]);
            String descricao = ValidadoresEntrada.obterTextoValidado(valores[2]);
            Tag tag = ValidadoresEntrada.obterTagValidado(valores[3]);
            String id_pessoa = ValidadoresEntrada.obterUUIDValidado(valores[4]);

            Pessoa pessoa = RepositoryPessoa.INSTANCE.buscarPessoa(id_pessoa);

            if (Objects.isNull(pessoa)) {
                System.out.println(MensagensErro.ERRO_PESSOA.getMensagem());
                return null;
            }

            PessoaDTO pessoaDTO = Conversores.converterParaDTO(pessoa);

            //Caso não tenha data de início, utiliza a atual
            LocalDateTime dataHoraInicio;
            if (!valores[5].isEmpty() && !valores[5].equals("null"))
                dataHoraInicio = ValidadoresEntrada.obterDataTimeValidada(valores[5]);
            else
                dataHoraInicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            //Permite carregar tarefas em andamento (sem data de término)
            if (valores.length > 6 && !valores[6].isEmpty() && !valores[6].equals("null")) {
                LocalDateTime dataHoraFim = ValidadoresEntrada.obterDataTimeValidada(valores[6]);

                if (!Validadores.validaDataFinal(dataHoraInicio, dataHoraFim)) {
                    System.out.println(MensagensErro.ERRO_DATA_FINAL.getMensagem());
                    return null;
                }

                return new Tarefa.Builder()
                        .id(id)
                        .titulo(titulo)
                        .descricao(descricao)
                        .tag(tag)
                        .pessoaDTO(pessoaDTO)
                        .dataHoraInicio(dataHoraInicio)
                        .dataHoraFim(dataHoraFim)
                        .build();
            }

            return new Tarefa.Builder()
                    .id(id)
                    .titulo(titulo)
                    .descricao(descricao)
                    .tag(tag)
                    .pessoaDTO(pessoaDTO)
                    .dataHoraInicio(dataHoraInicio)
                    .build();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Tarefa buscarTarefa(String id) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public Optional<List<Tarefa>> buscarTarefas(Predicate<Tarefa> predicate) {
        return Optional.of(tarefas.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }
}