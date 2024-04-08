package view;

import dto.TarefaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tarefa;
import utility.singleton.PessoaSingleton;
import utility.singleton.ProjetoSingleton;
import utility.singleton.TarefaSingleton;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuPessoa {
    public static void planilhaDeHoras() {

        //Retorna a planilha de horas conforme a pessoa participou.
        System.out.print("Digite o id do usuário: ");
        String pessoaId = MenuPrincipal.scanner.nextLine();
        Pessoa pessoa = PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId);

        System.out.println("\nPlanilha de horas\n" + "\nUsuário: " + pessoa.getUsername());

        listaProjetosPlanilha(ProjetoSingleton.INSTANCE.getRepositoryProjeto().carregarProjetos(), pessoaId);
        listaTarefasPlanilha(TarefaSingleton.INSTANCE.getRepositoryTarefa().carregarTarefas(), pessoaId);
    }

    public static void tempoTotalSemanal() {
        //Calcular o tempo total Semanal
    }

    public static void tempoTotalMensal() {
        //Calcular o tempo total Mensal
    }

    public static void rankingTempo() {
        //Calcular o ranking de tempo
    }

    public static void calcularMediaPorDia() {

        List<Projeto> projetos = ProjetoSingleton.INSTANCE.getRepositoryProjeto().carregarProjetos();
        String pessoaId = MenuPrincipal.scanner.nextLine();

        Duration diferenca;
        Duration tempoTotal = Duration.ZERO;
        long dias = 0;

        for (Projeto projeto : projetos) {
            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId).getId()))) {


                List<TarefaDTO> tarefaDTOList = projeto.getTarefasDTO();

                for (TarefaDTO tarefaDto : tarefaDTOList) {
                    if (tarefaDto.pessoa().id().equals(PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId).getId())) {

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

        List<Projeto> projetos = ProjetoSingleton.INSTANCE.getRepositoryProjeto().carregarProjetos();
        String pessoaId = MenuPrincipal.scanner.nextLine();
        Duration diferenca = Duration.ZERO;
        Duration soma = diferenca;
        int participacoes = 0;

        for (Projeto projeto : projetos) {

            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId).getId()))) {

                List<TarefaDTO> tarefaDTOList = projeto.getTarefasDTO();

                for (TarefaDTO tarefaDto : tarefaDTOList) {
                    if (tarefaDto.pessoa().id().equals(PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId).getId())) {
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
            if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId).getId()))) {

                LocalDateTime inicio = projeto.getDataHoraInicio();
                LocalDateTime fim = projeto.getDataHoraFim();

                Duration tempoTotal = Duration.between(inicio, fim);

                duracaoTotal = duracaoTotal.plus(tempoTotal);
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
            if (tarefa.getPessoaDTO().id().equals(PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(pessoaId).getId())) {
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

        long horasTotais = duracao.toHours();

        int horas = (int) (horasTotais % 24);
        int minutos = duracao.toMinutesPart();
        int segundos = duracao.toSecondsPart();

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