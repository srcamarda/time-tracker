package service;


import dto.PessoaDTO;
import model.Tarefa;
import utility.singleton.TarefaSingleton;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TarefaService {
    private static final long MAXIMO_DE_DURACAO_EM_MINUTOS = 8 * 60;
    private static final long DURACAO_MINIMA_DA_TAREFA_EM_MINUTOS = 5;

    public static boolean criarTarefa(Tarefa tarefa) {
        List<Tarefa> tarefas = buscarTarefa(tarefa.getPessoaDTO());

        // Verificar se é tarefa simultânea
        boolean existemTarefasSimultaneas = verificarSeTarefaEhSimultanea(tarefa, tarefas);
        if (existemTarefasSimultaneas) return false;

        // Calcular duração das tarefas no dia
        long duracaoTotal = obterDuracaoMaximaPorDia(tarefa, tarefas);
        if (duracaoTotal > MAXIMO_DE_DURACAO_EM_MINUTOS) return false;

        // Verificar duracao da tarefa
        if (tarefa.getDuracao().toMinutes() < DURACAO_MINIMA_DA_TAREFA_EM_MINUTOS) return false;

        // Adicionar tarefa
        TarefaSingleton.INSTANCE.getRepositoryTarefa().salvarTarefa(tarefa);
        return true;
    }

    public static List<Tarefa> buscarTarefa(String titulo) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasComTitulo(titulo.toLowerCase());
    }

    public static List<Tarefa> buscarTarefa(UUID pessoaId) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasPorPessoa(pessoaId);
    }

    public static List<Tarefa> buscarTarefa(PessoaDTO pessoaDTO) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasPorPessoa(pessoaDTO);
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
