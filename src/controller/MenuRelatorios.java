package controller;

import dto.TarefaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tarefa;
import repository.RepositoryPessoa;
import repository.RepositoryProjeto;
import repository.RepositoryTarefa;
import service.PessoaService;
import service.TarefaService;
import utility.Validadores;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class MenuRelatorios {

    static Scanner scanner = new Scanner(System.in);

    public static void planilhaDeHoras() {

        //Retorna a planilha de horas conforme a pessoa participou.
        System.out.print("Digite o id do usuário: ");
        String pessoaId = scanner.nextLine();
        Pessoa pessoa = RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId);

        System.out.println("\nPlanilha de horas\n" + "\nUsuário: " + pessoa.getUsername());

        listaProjetosPlanilha(RepositoryProjeto.INSTANCE.carregarProjetos(), pessoaId);
        listaTarefasPlanilha(RepositoryTarefa.INSTANCE.carregarTarefas(), pessoaId);
    }

    public static void relatorioNoPeriodo(String username, LocalDate dataInicio, LocalDate dataFim) {
        Pessoa pessoa = PessoaService.buscarPessoa(username);
        List<Tarefa> tarefas = TarefaService.buscarTarefas(pessoa.getId());
        long tempoTrabalhado = somatorioDeTempoTrabalhado(tarefas, dataInicio, dataFim);
        System.out.println("Tempo utilizado no Projeto do dia " + dataInicio.format(Validadores.formatter) + " ao dia " + dataInicio.format(Validadores.formatter) + " : " + tempoTrabalhado + " minutos\n");
    }
    private static LocalDate obterSegundaDaSemana(LocalDate data){
        int valorDiaSemanaTarefa = DayOfWeek.from(data).getValue();
        int valorSegunda = DayOfWeek.MONDAY.getValue();
        int diferencaAteSegunda = valorDiaSemanaTarefa - valorSegunda;
        return data.minusDays(diferencaAteSegunda);
    }
    
    public static void relatorioSemanal(String username, LocalDate dataSemana) {
        Pessoa pessoa = PessoaService.buscarPessoa(username);
        List<Tarefa> tarefas = TarefaService.buscarTarefas(pessoa.getId());
        LocalDate dataInicio = obterSegundaDaSemana(dataSemana);
        LocalDate dataFim = dataInicio.plusDays(4);
        long tempoTrabalhado = somatorioDeTempoTrabalhado(tarefas, dataInicio, dataFim);
        System.out.println("Tempo utilizado no Projeto durante a semana: " + tempoTrabalhado + " minutos\n");
    }

    public static void relatorioMensal(String username, LocalDate dataMes) {
        Pessoa pessoa = PessoaService.buscarPessoa(username);
        List<Tarefa> tarefas = TarefaService.buscarTarefas(pessoa.getId());
        LocalDate dataFim = dataMes.with(TemporalAdjusters.lastDayOfMonth());
        long tempoTrabalhado = somatorioDeTempoTrabalhado(tarefas, dataMes, dataFim);
        System.out.println("Tempo utilizado no Projeto durante a semana: " + tempoTrabalhado + " minutos\n");
    }

    private static long somatorioDeTempoTrabalhado(List<Tarefa> tarefas, LocalDate dataInicio, LocalDate dataFim) {
        return tarefas.stream().filter(tarefa -> !tarefa.getDataHoraInicio().toLocalDate().isBefore(dataInicio) && !tarefa.getDataHoraInicio().toLocalDate().isAfter(dataFim)).mapToLong(tarefa -> tarefa.getDuracao().toMinutes()).sum();
    }

    public static void rankingTempo() {
        //Calcular o ranking de tempo
    }

    public static void calcularMediaPorDia() {

        List<Projeto> projetos = RepositoryProjeto.INSTANCE.carregarProjetos();
        String pessoaId = scanner.nextLine();

        Duration diferenca;
        Duration tempoTotal = Duration.ZERO;
        long dias = 0;

        for (Projeto projeto : projetos) {
            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId()))) {


                List<TarefaDTO> tarefaDTOList = projeto.getTarefasDTO();

                for (TarefaDTO tarefaDto : tarefaDTOList) {
                    if (tarefaDto.pessoa().id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId())) {

                        LocalDateTime inicioTarefa = tarefaDto.dataHoraInicio();
                        LocalDateTime fimTarefa = tarefaDto.dataHoraFim();

                        diferenca = Duration.between(inicioTarefa, fimTarefa);

                        tempoTotal = tempoTotal.plus(diferenca);
                        dias = diferenca.toDays();
                    }
                }
            }
        }

        if (dias > 0) {
            System.out.println(tempoFormatado(tempoTotal.dividedBy(dias)));
        }
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

        Duration duracaoTotal = Duration.ZERO;

        System.out.println("\nProjetos");

        for (Projeto projeto : projetos) {
            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId()))) {

                LocalDateTime inicio = projeto.getDataHoraInicio();
                LocalDateTime fim = projeto.getDataHoraFim();

                System.out.println(tempoFormatado(Duration.between(inicio, fim)));

                duracaoTotal = duracaoTotal.plus(Duration.between(inicio, fim));
                System.out.println("\nTitulo projeto: " + projeto.getTitulo() +
                        "\nData de Inicio projeto:" + diaFormatado(inicio) +
                        "\nData de fim projeto: " + diaFormatado(fim) +
                        "\nTempo no projeto: " + tempoFormatado(duracaoTotal));
            }
        }
    }

    private static void listaTarefasPlanilha(List<Tarefa> tarefas, String pessoaId) {

        Duration duracaoTotal = Duration.ZERO;

        System.out.println("\nTarefas do Usúario");

        for (Tarefa tarefa : tarefas) {
            if (tarefa.getPessoaDTO().id().equals(RepositoryPessoa.INSTANCE.buscarPessoa(pessoaId).getId())) {
                LocalDateTime inicio = tarefa.getDataHoraInicio();
                LocalDateTime fim = tarefa.getDataHoraFim();

                Duration tempoTotal = Duration.between(inicio, fim);

                duracaoTotal = duracaoTotal.plus(tempoTotal);

                System.out.println("\nTitulo tarefa: " + tarefa.getTitulo() +
                        "\nData de Inicio:" + diaFormatado(inicio) +
                        "\nData de fim: " + diaFormatado(fim) +
                        "\nTempo na tarefa: " + tempoFormatado(duracaoTotal));
            }
        }
    }

    private static String tempoFormatado(Duration duracao) {

        System.out.println(duracao);

        long horasTotais = duracao.toHours();
        long minutosTotais = duracao.toMinutes();
        long segundosTotais = duracao.toSeconds();

        System.out.print(duracao.toDays());

        long horas = (horasTotais % 24);
        long minutos = (minutosTotais % 60);
        long segundos = (segundosTotais % 60);

        String horasFormatadas = (horas < 10) ? "0" + horas : String.valueOf(horas);
        String minutosFormatados = (minutos < 10) ? "0" + minutos : String.valueOf(minutos);
        String segundosFormatados = (segundos < 10) ? "0" + segundos : String.valueOf(segundos);

        return horasFormatadas + ":" + minutosFormatados + ":" + segundosFormatados;
    }

    private static String diaFormatado(LocalDateTime data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }
}