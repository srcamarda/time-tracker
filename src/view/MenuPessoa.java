package view;

import model.Pessoa;
import model.Projeto;
import model.Tarefa;
import repository.ArquivoPaths;
import repository.RepositoryPessoa;
import repository.RepositoryProjeto;
import repository.RepositoryTarefa;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuPessoa {

    //Declaracoes para arquivos e puxar os dados dos arqquivos
    private static final ArquivoPaths arquivoPessoa = ArquivoPaths.PESSOAS;
    private static final ArquivoPaths arquivoProjeto = ArquivoPaths.PROJETOS;
    private static final ArquivoPaths arquivoTarefas = ArquivoPaths.TAREFAS;
    private static final RepositoryPessoa repositoryPessoa = new RepositoryPessoa(arquivoPessoa);
    private static final RepositoryProjeto repositoryProjeto = new RepositoryProjeto(arquivoProjeto);
    private static final RepositoryTarefa repositoryTarefa = new RepositoryTarefa(arquivoTarefas);

    public static void tempoTotalSemanal() {
        //Calcular o tempo total Semanal
    }

    public static void tempoTotalMensal() {
        //Calcular o tempo total Mensal
    }

    public static void planiliaDeHoras() {

        //Retorna a planilia de horas conforme a pessoa participou.
        System.out.print("Digite o id do usuário: ");
        String pessoaId = MenuPrincipal.scanner.nextLine();

        List<Projeto> projetos = repositoryProjeto.carregarProjetos();
        List<Tarefa> tarefas = repositoryTarefa.carregarTarefas();

                Pessoa pessoa = repositoryPessoa.buscarPessoa(pessoaId);

                System.out.println("\nPlanilia de horas\n" + "\nUsuário: " + pessoa.getUsername());

                listaProjetosPlanilia(projetos, pessoaId);
                listaTarefasPlanilia(tarefas, pessoaId);

        }


    public static void calcularMediaTempoGeral() {

        String id = MenuPrincipal.scanner.nextLine();
        Duration duracaoTotal = obterTempoProjetos(id);

        long totalDias = duracaoTotal.toDays();

        if (totalDias > 0) {
            Duration mediaGeral = duracaoTotal.dividedBy(totalDias);
            System.out.println(tempoFormatado(duracaoTotal));
        }
    }

    //Corrigir
    public static void calcularMediaPorDia() {

        //Corrigir para dividir o total de dias pelas horas;

        String id = MenuPrincipal.scanner.nextLine();

        Duration duracaoTotal = obterTempoProjetos(id);
        System.out.println("Duracao Total " + tempoFormatado(duracaoTotal));

        System.out.println(tempoFormatado(duracaoTotal));
    }

    public static void rankingTempo() {
        //Percorrer o projeto e retornar as pessoas com mais tempo;

    }

    //Corrigir
    public static Duration obterTempoProjetos(String pessoaId) {

        //Carrega os projetos e pesquisa pessoas
        List<Projeto> projetos = repositoryProjeto.carregarProjetos();
        Pessoa pesquisaPessoa = repositoryPessoa.buscarPessoa(pessoaId);

        //conta as participações e cria duracao do projeto
        int participacoes = 0;
        Duration duracaoTotal = Duration.ZERO;

        if (pesquisaPessoa != null) {
            for (Projeto projeto : projetos) {
                if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(pesquisaPessoa.getId()))) {

                    LocalDateTime inicio = projeto.getDataHoraInicio();
                    System.out.print("\ninicio hora - " + inicio.getHour() + " ini min - " + inicio.getMinute() + " ini sec - " + inicio.getSecond() + "\n");

                    LocalDateTime fim = projeto.getDataHoraFim();
                    System.out.print("\nFim hora - " + fim.getHour() + " Fim min - " + fim.getMinute() + " Fim sec - " + fim.getSecond() + "\n");

                    Duration duracaoProjeto = Duration.between(inicio, fim);

                    System.out.println(participacoes + " - Duração do projeto - " + tempoFormatado(duracaoProjeto));

                    duracaoTotal = duracaoTotal.plus(duracaoProjeto);
                    //Está somando até certo ponto após 2 projetos a mais ele reduz
                    System.out.println("\n" + participacoes + " Duracao Total apos soma->" + tempoFormatado(duracaoTotal));

                    participacoes++;
                }
            }

            if (participacoes > 0) {

                return duracaoTotal;
            }

            System.out.println("Nenhuma participação encontrada");
            return Duration.ZERO;
        }

        System.out.println("Id não encontrado");
        return Duration.ZERO;
    }


    private static void listaProjetosPlanilia(List<Projeto> projetos, String pessoaId) {

        Pessoa pesquisaPessoa = repositoryPessoa.buscarPessoa(pessoaId);

        Duration duracaoTotal = Duration.ZERO;


            System.out.println("\nProjetos");

            for (Projeto projeto : projetos) {
                if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(pesquisaPessoa.getId()))) {
                    LocalDateTime inicio = projeto.getDataHoraInicio();
                    LocalDateTime fim = projeto.getDataHoraFim();

                    Duration tempoTotal = Duration.between(inicio, fim);

                    duracaoTotal = duracaoTotal.plus(tempoTotal);
                    System.out.println("\nTitulo projeto: " + projeto.getTitulo() +
                                       "\nData de Inicio projeto:" + diaFormatado(inicio) +
                                       "\nData de fim projeto: " + diaFormatado(fim) +
                                       "\nTempo total no projeto: " + tempoFormatado(tempoTotal));
                }
            }
    }

    private static void listaTarefasPlanilia(List<Tarefa> tarefas, String pessoaId) {

        Pessoa pesquisaPessoa = repositoryPessoa.buscarPessoa(pessoaId);

        Duration duracaoTotal = Duration.ZERO;

            System.out.println("\nTarefas");

            for (Tarefa tarefa : tarefas) {
                if (tarefa.getPessoaDTO().id().equals(pesquisaPessoa.getId())) {
                    LocalDateTime inicio = tarefa.getDataHoraInicio();
                    LocalDateTime fim = tarefa.getDataHoraFim();

                    Duration tempoTotal = Duration.between(inicio, fim);

                    duracaoTotal = duracaoTotal.plus(tempoTotal);
                    System.out.println("\nTitulo tarefa: " + tarefa.getTitulo() +
                                       "\nData de Inicio projeto:" + diaFormatado(inicio) +
                                       "\nData de fim projeto: " + diaFormatado(fim) +
                                       "\nTempo total no projeto: " + tempoFormatado(tempoTotal));
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

        StringBuilder resultado = new StringBuilder();
        resultado.append(horasFormatadas).append(":").append(minutosFormatados).append(":").append(segundosFormatados);

        return resultado.toString();
    }

    private static String diaFormatado(LocalDateTime data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

}

