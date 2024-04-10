package controller;

import utility.Conversores;

import java.time.LocalDate;
import java.util.Scanner;

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
}