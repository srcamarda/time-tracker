package repository;

import model.Tag;
import model.Tarefa;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class RepositoryTarefa {
    ArquivoUtil arquivo;
    HashSet<Tarefa> tarefas;
    RepositoryPessoa repositoryPessoa;

    public RepositoryTarefa(ArquivoPaths path, RepositoryPessoa repositoryPessoa) {
        arquivo = new ArquivoUtil(path);
        tarefas = carregarTarefas();
        this.repositoryPessoa = repositoryPessoa;
    }

    public HashSet<Tarefa> carregarTarefas() {
        List<String> tarefasStr = arquivo.lerArquivo();
        HashSet<Tarefa> tarefas = new HashSet<>();
        tarefasStr.stream().skip(1).map((this::tarefaParser)).forEach(tarefas::add);
        return tarefas;
    }

    public void salvarTarefas(List<Tarefa> tarefas) {
        tarefas.forEach(this::salvarTarefa);
    }

    public void salvarTarefa(Tarefa Tarefa) {
        String tarefaStr = Tarefa.getId() + ";" + Tarefa.getTitulo() + ";" + Tarefa.getDescricao() + ";" + Tarefa.getTag() + ";" + Tarefa.getPessoa() + ";" + Tarefa.getDataHoraInicio() + ";" + Tarefa.getDataHoraFim();
        arquivo.escreverArquivo(tarefaStr);
    }

    public Tarefa tarefaParser(String linha) {
        String[] valores = linha.split(";");

        return new Tarefa.Builder()
                .id(valores[0])
                .titulo(valores[1])
                .descricao(valores[2])
                .tag(Tag.valueOf(valores[3]))
                .pessoa(repositoryPessoa.buscarPessoa(valores[4]))
                .dataHoraInicio(LocalDateTime.parse(valores[4]))
                .dataHoraFim(LocalDateTime.parse(valores[5]))
                .build();
    }

    public Tarefa buscarTarefa(String id) {
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId().toString().equals(id)) {
                return tarefa;
            }
        }
        return null;
    }
}