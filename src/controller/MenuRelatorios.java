package controller;

import dto.PessoaDTO;
import dto.ProjetoDTO;
import dto.TarefaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tarefa;
import repository.RepositoryPessoa;
import repository.RepositoryProjeto;
import repository.RepositoryTarefa;
import service.PessoaService;
import service.TarefaService;
import utility.Conversores;
import utility.Validadores;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MenuRelatorios {

    static Scanner scanner = new Scanner(System.in);

    public static void planilhaDeHoras(String username) {

        Pessoa pessoa = PessoaService.buscarPessoa(username);

        System.out.println("\nPlanilha de horas\n" + "\nUsuário: " + username);

        listaProjetosPlanilha(RepositoryProjeto.INSTANCE.carregarProjetos(), pessoa.getId().toString());
        listaTarefasPlanilha(RepositoryTarefa.INSTANCE.carregarTarefas(), pessoa.getId().toString());
    }

    public static void calcularMediaPorDia() {
        System.out.print("Digite o título do projeto: ");
        String titulo = scanner.nextLine();

        Projeto projeto = buscarProjeto(titulo);

        if (projeto == null) {
            System.out.println("Projeto não encontrado.");
            return;
        }

        // Agrupa tarefas por pessoa e calcula o tempo total e a média para cada uma
        projeto.getTarefasDTO().stream()
                .collect(Collectors.groupingBy(tarefaDto -> Conversores.converterParaModel(tarefaDto.pessoa())))
                .forEach((pessoa, tarefas) -> {
                    Duration tempoTotalPessoa = tarefas.stream()
                            .map(tarefa -> Duration.between(tarefa.dataHoraInicio(), tarefa.dataHoraFim()))
                            .reduce(Duration.ZERO, Duration::plus);
                    Set<LocalDate> diasTrabalhados = tarefas.stream()
                            .flatMap(tarefa -> getDatesBetween(tarefa.dataHoraInicio(), tarefa.dataHoraFim()).stream())
                            .collect(Collectors.toSet());

                    if (!diasTrabalhados.isEmpty()) {
                        Duration mediaPorDia = tempoTotalPessoa.dividedBy(diasTrabalhados.size());
                        System.out.println(pessoa.getNome() + ": " + tempoFormatado(mediaPorDia));
                    }
                });

        // Calcular e imprimir o tempo médio total do projeto
        long numeroDiasProjeto = projeto.getTarefasDTO().stream()
                .flatMap(tarefa -> getDatesBetween(tarefa.dataHoraInicio(), tarefa.dataHoraFim()).stream())
                .collect(Collectors.toSet()).size();

        Duration tempoTotalProjeto = projeto.getTarefasDTO().stream()
                .map(tarefa -> Duration.between(tarefa.dataHoraInicio(), tarefa.dataHoraFim()))
                .reduce(Duration.ZERO, Duration::plus);
    }

    private static Projeto buscarProjeto(String titulo) {
        // Implementação do método que usa o título para buscar o projeto.
        // Este método é uma simplificação. Sua implementação real dependerá do seu repositório.
        Predicate<Projeto> predicate = projeto -> projeto.getTitulo().equalsIgnoreCase(titulo);
        return RepositoryProjeto.INSTANCE.buscarProjetos(predicate).orElseThrow(() -> new NoSuchElementException("Projeto com título '" + titulo + "' não encontrado")).get(0);
    }

    private static Set<LocalDate> getDatesBetween(LocalDateTime start, LocalDateTime end) {
        Set<LocalDate> dates = new HashSet<>();
        LocalDate startDay = start.toLocalDate();
        LocalDate endDay = end.toLocalDate();
        while (!startDay.isAfter(endDay)) {
            dates.add(startDay);
            startDay = startDay.plusDays(1);
        }
        return dates;
    }

    public static void calcularMediaTempoGeral() {

        List<Projeto> projetos = RepositoryProjeto.INSTANCE.carregarProjetos();
        String pessoaId = scanner.nextLine();
        Duration diferenca = Duration.ZERO;
        Duration soma = diferenca;
        int participacoes = 0;

        for (Projeto projeto : projetos) {

            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId()))) {

                List<TarefaDTO> tarefaDTOList = projeto.getTarefasDTO();

                for (TarefaDTO tarefaDto : tarefaDTOList) {
                    if (tarefaDto.pessoa().id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId())) {
                        LocalDateTime inicioTarefa = tarefaDto.dataHoraInicio();
                        LocalDateTime fimTarefa = tarefaDto.dataHoraFim();

                        diferenca = Duration.between(inicioTarefa, fimTarefa);
                        System.out.println(tempoFormatado(diferenca));

                        soma = soma.plus(diferenca);

                        participacoes++;
                    }
                }
            }
            System.out.println("Nenhuma participacao encontrada");
        }
        if (participacoes > 0) {
            System.out.println(tempoFormatado(soma.dividedBy(participacoes)));
        } else {
            System.out.println("Nenhuma participacao encontrada");
        }
    }

    private static void listaProjetosPlanilha(List<Projeto> projetos, String pessoaId) {
        System.out.println("\nProjetos Participados:");
        System.out.println("----------------------");

        Duration duracaoTotal = Duration.ZERO;

        for (Projeto projeto : projetos) {
            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId()))) {

                LocalDateTime inicio = projeto.getDataHoraInicio();
                LocalDateTime fim = projeto.getDataHoraFim();
                Duration duracaoProjeto = Duration.between(inicio, fim);

                duracaoTotal = duracaoTotal.plus(duracaoProjeto);

                System.out.println("Titulo projeto: " + projeto.getTitulo() +
                        "\nData de Inicio projeto: " + diaFormatado(inicio) +
                        "\nData de fim projeto: " + diaFormatado(fim) +
                        "\nTempo no projeto: " + tempoFormatado(duracaoProjeto) + "\n");
            }
        }
        System.out.println("Tempo total em projetos: " + tempoFormatado(duracaoTotal));
    }

    private static void listaTarefasPlanilha(List<Tarefa> tarefas, String pessoaId) {
        System.out.println("\nTarefas Atribuídas:");
        System.out.println("-------------------");

        Duration duracaoTotal = Duration.ZERO;

        for (Tarefa tarefa : tarefas) {
            if (tarefa.getPessoaDTO().id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId())) {
                LocalDateTime inicio = tarefa.getDataHoraInicio();
                LocalDateTime fim = tarefa.getDataHoraFim();
                Duration duracaoTarefa = Duration.between(inicio, fim);

                duracaoTotal = duracaoTotal.plus(duracaoTarefa);

                System.out.println("Titulo tarefa: " + tarefa.getTitulo() +
                        "\nData de Inicio: " + diaFormatado(inicio) +
                        "\nData de fim: " + diaFormatado(fim) +
                        "\nTempo na tarefa: " + tempoFormatado(duracaoTarefa) + "\n");
            }
        }
        System.out.println("Tempo total em tarefas: " + tempoFormatado(duracaoTotal));
    }

    private static String tempoFormatado(Duration duracao) {
        long dias = duracao.toDays();
        long horas = duracao.toHoursPart();
        long minutos = duracao.toMinutesPart();

        if (dias > 0) {
            return String.format("%d dias, %d horas, %d minutos", dias, horas, minutos);
        } else {
            return String.format("%d horas, %d minutos", horas, minutos);
        }
    }

    private static String diaFormatado(LocalDateTime data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public static void relatorioSemanal(String username, LocalDate dataSemana) {
        Pessoa pessoa = PessoaService.buscarPessoa(username);
        List<Tarefa> tarefas = TarefaService.buscarTarefas(pessoa.getId());
        LocalDate dataInicio = obterSegundaDaSemana(dataSemana);
        LocalDate dataFim = dataInicio.plusDays(4);
        long tempoTrabalhado = somatorioDeTempoTrabalhado(tarefas, dataInicio, dataFim);
        if (tempoTrabalhado == 0) {
            System.out.println("Não a registro de tempo de trabalho na semana escolhida.");
        } else {
            System.out.println("Tempo utilizado no Projeto durante a semana: " + tempoTrabalhado + " minutos\n");
        }
    }

    public static void relatorioMensal(String username, LocalDate dataMes) {
        Pessoa pessoa = PessoaService.buscarPessoa(username);
        List<Tarefa> tarefas = TarefaService.buscarTarefas(pessoa.getId());
        LocalDate dataFim = dataMes.with(TemporalAdjusters.lastDayOfMonth());
        long tempoTrabalhado = somatorioDeTempoTrabalhado(tarefas, dataMes, dataFim);
        if (tempoTrabalhado == 0) {
            System.out.println("Não a registro de tempo de trabalho no mês escolhido.");
        } else {
            System.out.println("Tempo utilizado no Projeto durante a semana: " + tempoTrabalhado + " minutos\n");
        }
    }

    public static void relatorioNoPeriodo(String username, LocalDate dataInicio, LocalDate dataFim) {
        Pessoa pessoa = PessoaService.buscarPessoa(username);
        List<Tarefa> tarefas = TarefaService.buscarTarefas(pessoa.getId());
        long tempoTrabalhado = MenuRelatorios.somatorioDeTempoTrabalhado(tarefas, dataInicio, dataFim);
        if (tempoTrabalhado == 0) {
            System.out.println("Não a registro de tempo de trabalho no periodo escolhido.");
        } else {
            System.out.println("Tempo utilizado no Projeto do dia " + dataInicio.format(Validadores.formatter) + " ao dia " + dataInicio.format(Validadores.formatter) + " : " + tempoTrabalhado + " minutos\n");
        }
    }

    private static LocalDate obterSegundaDaSemana(LocalDate data) {
        int valorDiaSemanaTarefa = DayOfWeek.from(data).getValue();
        int valorSegunda = DayOfWeek.MONDAY.getValue();
        int diferencaAteSegunda = valorDiaSemanaTarefa - valorSegunda;
        return data.minusDays(diferencaAteSegunda);
    }

    static long somatorioDeTempoTrabalhado(List<Tarefa> tarefas, LocalDate dataInicio, LocalDate dataFim) {
        return tarefas.stream().filter(tarefa -> !tarefa.getDataHoraInicio().toLocalDate().isBefore(dataInicio) && !tarefa.getDataHoraInicio().toLocalDate().isAfter(dataFim)).mapToLong(tarefa -> tarefa.getDuracao().toMinutes()).sum();
    }
}