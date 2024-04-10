package service;


import dto.PessoaDTO;
import model.Tarefa;
import repository.RepositoryTarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TarefaService {
    private static final long MAXIMO_DE_DURACAO_EM_MINUTOS = 8 * 60;
    private static final long DURACAO_MINIMA_DA_TAREFA_EM_MINUTOS = 5;

    public static boolean criarTarefa(Tarefa tarefa) {
        List<Tarefa> tarefas = buscarTarefas(tarefa.getPessoaDTO());

        // Verificar se é tarefa simultânea
        boolean existemTarefasSimultaneas = verificarSeTarefaEhSimultanea(tarefa, tarefas);
        if (existemTarefasSimultaneas) return false;

        // Calcular duração das tarefas no dia
        long duracaoTotal = obterDuracaoMaximaPorDia(tarefa, tarefas);
        if (duracaoTotal > MAXIMO_DE_DURACAO_EM_MINUTOS) return false;

        // Verificar duracao da tarefa
        if (tarefa.getDuracao().toMinutes() < DURACAO_MINIMA_DA_TAREFA_EM_MINUTOS) return false;

        // Adicionar tarefa
        RepositoryTarefa.INSTANCE.salvarTarefa(tarefa);
        return true;
    }

    public static Tarefa buscarTarefa(String titulo) {
        Predicate<Tarefa> predicate = tarefa -> tarefa.getTitulo().trim().equalsIgnoreCase(titulo.trim());
        return RepositoryTarefa.INSTANCE.buscarTarefas(predicate).orElseThrow().get(0);
    }

    public static List<Tarefa> buscarTarefas(String titulo) {
        Predicate<Tarefa> predicate = tarefa -> tarefa.getTitulo().trim().toLowerCase().contains(titulo.toLowerCase().trim());
        return RepositoryTarefa.INSTANCE.buscarTarefas(predicate).orElseThrow();
    }

    public static List<Tarefa> buscarTarefas(UUID pessoaId) {
        Predicate<Tarefa> predicate = tarefa -> tarefa.getPessoaDTO().id().equals(pessoaId);
        return RepositoryTarefa.INSTANCE.buscarTarefas(predicate).orElseThrow();
    }

    public static List<Tarefa> buscarTarefas(PessoaDTO pessoaDTO) {
        Predicate<Tarefa> predicate = tarefa -> tarefa.getPessoaDTO().equals(pessoaDTO);
        return RepositoryTarefa.INSTANCE.buscarTarefas(predicate).orElseThrow();
    }

    private static long obterDuracaoMaximaPorDia(Tarefa tarefa, List<Tarefa> tarefas) {
        LocalDate dataDaTarefa = tarefa.getDataHoraFim().toLocalDate();
        List<Tarefa> tarefasNoMesmoDia = tarefas.stream().filter(tarefaExistente -> tarefaExistente.getDataHoraInicio().toLocalDate().equals(dataDaTarefa)).toList();

        long totalDeMinutos = tarefasNoMesmoDia.stream()
                .mapToLong(tarefaExistente -> tarefaExistente.getDuracao().toMinutes())
                .sum();

        return totalDeMinutos + tarefa.getDuracao().toMinutes();
    }

    private static boolean verificarSeTarefaEhSimultanea(Tarefa tarefa, List<Tarefa> tarefas) {
        Predicate<Tarefa> ehTarefaSimultanea = tarefa1 ->
                comecaDuranteOutraTarefa(tarefa, tarefa1) || terminaDuranteOutraTarefa(tarefa, tarefa1);
        List<Tarefa> tarefasSimultaneas = tarefas.stream().filter(ehTarefaSimultanea).toList();

        return !tarefasSimultaneas.isEmpty();
    }

    private static boolean terminaDuranteOutraTarefa(Tarefa tarefaNova, Tarefa tarefaExistente) {
        return tarefaExistente.getDataHoraInicio().isBefore(tarefaNova.getDataHoraFim()) &&
                tarefaExistente.getDataHoraFim().isAfter(tarefaNova.getDataHoraFim());
    }

    private static boolean comecaDuranteOutraTarefa(Tarefa tarefaNova, Tarefa tarefaExistente) {
        return tarefaExistente.getDataHoraInicio().isBefore(tarefaNova.getDataHoraInicio()) &&
                tarefaExistente.getDataHoraFim().isAfter(tarefaNova.getDataHoraInicio());
    }
}
