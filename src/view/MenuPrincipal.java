package view;

import controller.MenuAcoes;
import view.mensagens.MensagensMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

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
                        MenuAcoes.planilhaDeHoras();
                        break;
                    case 2:
                        menuRelatoriosAtividades();
                        break;
                    case 3:
                        menuRelatoriosIndicadores();
                        break;
                    case 4:
                        System.out.println(MensagensMenu.SAIR_MENU);
                        break;
                    default:
                        System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (menuOption != 4);
    }

    private static void menuRelatoriosAtividades() {
        System.out.println(MensagensMenu.REL_ATIVIDADES.getMensagem());
        int subMenuOption = 0;

        do {
            MenuExibir.RelatoriosAtividadesMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1:
                        MenuAcoes.tempoTotalSemanal();
                        break;
                    case 2:
                        MenuAcoes.tempoTotalMensal();
                        break;
                    case 3:
                        System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());
                        break;
                    default:
                        System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }
        } while (subMenuOption != 3);
    }

    private static void menuRelatoriosIndicadores() {
        System.out.println(MensagensMenu.REL_INDICADORES.getMensagem());
        int subMenuOption = 0;

        do {
            MenuExibir.RelatoriosIndicadoresMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1:
                        MenuAcoes.mediaTempoPorDia();
                        break;
                    case 2:
                        MenuAcoes.mediaTempoGeral();
                        break;
                    case 3:
                        MenuAcoes.rankingTempo();
                        break;
                    case 4:
                        System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());
                        break;
                    default:
                        System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (subMenuOption != 4);
    }
}