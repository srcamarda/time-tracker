package controller;

import utility.Conversores;

import java.time.LocalDate;
import java.util.Scanner;

import repository.RepositoryPessoa;
import service.ProjetoService;
import service.TarefaService;

public class MenuAcoes {

    private static Scanner scanner = new Scanner(System.in);

    public static void planilhaDeHoras() {
        System.out.println("Opção selecionada: Planilha de Horas");
        MenuRelatorios.planilhaDeHoras();
    }

    public static void tempoTotalSemanal() {
        System.out.println("Opção selecionada: Tempo Total Semanal");
        System.out.print("Digite o username do usuário: ");
        String pessoaUsername = scanner.nextLine();
        System.out.println("Digite uma data na semana desejada dd/MM/yyyy: ");
        LocalDate dataString = Conversores.converterStringParaDate(scanner.nextLine());
        MenuRelatorios.relatorioSemanal(pessoaUsername, dataString);
    }

    public static void tempoTotalMensal() {
        System.out.println("Opção selecionada: Tempo Total Mensal");
        MenuRelatorios.relatorioMensal();
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
        //TODO
    }

    public static void listarPessoas() {
        System.out.printf("\n%-10s    %-15s", "Nome", "Cargo");
        RepositoryPessoa.INSTANCE.getPessoas().forEach(pessoa -> System.out.printf("\n%-15s    %-15s",pessoa.getNome(), pessoa.getCargo()));
        System.out.println();
    }

    public static void cadastrarTarefa() {
        //TODO
    }

    public static void listarTarefas() {
        System.out.println(TarefaService.buscarTarefas(""));
    }

    public static void cadastrarProjeto() {
        //TODO
    }

    public static void listarProjetos() {
        System.out.println(ProjetoService.buscarProjetos(""));
    }
}