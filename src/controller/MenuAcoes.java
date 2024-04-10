package controller;

import repository.RepositoryPessoa;
import service.ProjetoService;
import service.TarefaService;

public class MenuAcoes {
    public static void planilhaDeHoras() {
        System.out.println("Opção selecionada: Planilha de Horas");
        MenuRelatorios.planilhaDeHoras();
    }

    public static void tempoTotalSemanal() {
        System.out.println("Opção selecionada: Tempo Total Semanal");
        MenuRelatorios.tempoTotalSemanal();
    }

    public static void tempoTotalMensal() {
        System.out.println("Opção selecionada: Tempo Total Mensal");
        MenuRelatorios.tempoTotalMensal();
    }

    public static void mediaTempoPorDia() {
        System.out.println("Opção selecionada: Média de Tempo por Dia");
        MenuRelatorios.calcularMediaPorDia();
    }

    public static void mediaTempoGeral() {
        System.out.println("Opção selecionada: Média de Tempo Geral");
        MenuRelatorios.calcularMediaTempoGeral();
    }

    public static void rankingTempo() {
        System.out.println("Opção selecionada: Ranking de Tempo");
        MenuRelatorios.rankingTempo();
    }

    public static void cadastrarPessoa() {
    }

    public static void removerPessoa() {
    }

    public static void listarPessoas() {
        System.out.printf("\n%-10s    %-15s", "Nome", "Cargo");
        RepositoryPessoa.INSTANCE.getPessoas().forEach(pessoa -> System.out.printf("\n%-15s    %-15s",pessoa.getNome(), pessoa.getCargo()));
        System.out.println();
    }

    public static void cadastrarTarefa() {
    }

    public static void removerTarefa() {
    }

    public static void listarTarefas() {
        System.out.println(TarefaService.buscarTarefas(""));
    }

    public static void cadastrarProjeto() {
    }

    public static void removerProjeto() {
    }

    public static void listarProjetos() {
        System.out.println(ProjetoService.buscarProjeto(""));
    }
}