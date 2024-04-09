package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    MenuPrincipal() {
        MenuTarefa menuTarefa;
        MenuPessoa menuPessoa;
        MenuProjeto menuProjeto;
    }

    static Scanner scanner = new Scanner(System.in);

    public static void mainMenu() {
        int menuOption = 0;

        do {
            MenuExibir.menuPrincipal();

            try {
                menuOption = scanner.nextInt();
                scanner.nextLine();

                switch (menuOption) {
                    case 1:
                        planilhaDeHoras();
                        break;
                    case 2:
                        relatoriosAtividades();
                        break;
                    case 3:
                        relatoriosIndicadores();
                        break;
                    case 4:
                        System.out.println("Até logo!");
                        break;
                    default:
                        System.out.println("Escolha uma opção valida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite um número.");
                scanner.nextLine();
            }

        } while (menuOption != 4);
    }

    private static void relatoriosAtividades() {
        System.out.println("Relatórios de Atividades");
        int subMenuOption = 0;

        do {
            MenuExibir.RelatoriosAtividadesMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1:
                        tempoTotalSemanal();
                        break;
                    case 2:
                        tempoTotalMensal();
                        break;
                    case 3:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite um número.");
                scanner.nextLine();
            }
        } while (subMenuOption != 3);
    }

    private static void relatoriosIndicadores() {
        System.out.println("Relatórios de Indicadores");
        int subMenuOption = 0;

        do {
            MenuExibir.RelatoriosIndicadoresMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1:
                        mediaTempoPorDia();
                        break;
                    case 2:
                        mediaTempoGeral();
                        break;
                    case 3:
                        rankingTempo();
                        break;
                    case 4:
                        System.out.println("Retornando para o menu principal");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite um número valido!!");
                scanner.nextLine();
            }

        } while (subMenuOption != 4);
    }

    private static void planilhaDeHoras() {
        System.out.println("Opção selecionada: Planilha de Horas");
        MenuPessoa.planilhaDeHoras();
    }

    private static void tempoTotalSemanal() {
        System.out.println("Opção selecionada: Tempo Total Semanal");
        MenuPessoa.tempoTotalSemanal();
    }

    private static void tempoTotalMensal() {
        System.out.println("Opção selecionada: Tempo Total Mensal");
        MenuPessoa.tempoTotalMensal();
    }

    private static void mediaTempoPorDia() {
        System.out.println("Opção selecionada: Média de Tempo por Dia");
        MenuPessoa.calcularMediaPorDia();
    }

    private static void mediaTempoGeral() {
        System.out.println("Opção selecionada: Média de Tempo Geral");
        MenuPessoa.calcularMediaTempoGeral();
    }

    private static void rankingTempo() {
        System.out.println("Opção selecionada: Ranking de Tempo");
        MenuPessoa.rankingTempo();
    }
}