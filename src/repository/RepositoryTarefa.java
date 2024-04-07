package repository;

import dto.PessoaDTO;
import model.Tag;
import model.Tarefa;
import utility.Conversores;
import utility.singleton.PessoaSingleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositoryTarefa {
    ArquivoUtil arquivo;
    List<Tarefa> tarefas;

    public RepositoryTarefa(ArquivoPaths path) {
        arquivo = new ArquivoUtil(path);
        tarefas = carregarTarefas();
    }

    public List<Tarefa> carregarTarefas() {
        List<String> tarefasStr = arquivo.lerArquivo();
        List<Tarefa> tarefas = new ArrayList<>();
        tarefasStr.stream().skip(1).map((this::tarefaParser)).forEach(tarefas::add);
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
    }

    public Tarefa tarefaParser(String linha) {
        String[] valores = linha.split(";");

        PessoaDTO pessoaDTO = Conversores.converterParaDTO(
                PessoaSingleton
                        .INSTANCE
                        .getRepositoryPessoa()
                        .buscarPessoa(valores[4]));

        return new Tarefa.Builder()
                .id(valores[0])
                .titulo(valores[1])
                .descricao(valores[2])
                .tag(Tag.valueOf(valores[3]))
                .pessoaDTO(pessoaDTO)
                .dataHoraInicio(LocalDateTime.parse(valores[5]))
                .dataHoraFim(LocalDateTime.parse(valores[6]))
                .build();
    }

    public Tarefa buscarTarefa(String id) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public List<Tarefa> buscarTarefasComTitulo(String titulo) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Tarefa> buscarTarefasPorPessoa(UUID pessoaId) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getPessoaDTO().id().equals(pessoaId))
                .collect(Collectors.toList());
    }
    public List<Tarefa> buscarTarefasPorPessoa(PessoaDTO pessoaDTO) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getPessoaDTO().nome().equalsIgnoreCase(pessoaDTO.nome()))
                .collect(Collectors.toList());
    }
}