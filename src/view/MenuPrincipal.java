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
                    case 1: //Planilha de horas
                        MenuAcoes.planilhaDeHoras();
                        break;
                    case 2: //Relatórios de atividades
                        menuRelatoriosAtividades();
                        break;
                    case 3: //Relatórios de indicadores
                        menuRelatoriosIndicadores();
                        break;
                    case 4: //Gerenciar pessoas
                        menuGerenciarPessoas();
                        break;
                    case 5: //Gerenciar projetos
                        menuGerenciarProjetos();
                        break;
                    case 6: //Gerenciar tarefas
                        menuGerenciarTarefas();
                        break;
                    case 7: //Sair
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

    private static void menuGerenciarPessoas() {
        int subMenuOption = 0;

        do {
            MenuExibir.gerenciarPessoasMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1: //Cadastrar pessoa
                        MenuAcoes.cadastrarPessoa();
                        break;
                    case 2: //Remover pessoa
                        MenuAcoes.removerPessoa();
                        break;
                    case 3: //Listar pessoas
                        MenuAcoes.listarPessoas();
                        break;
                    case 4: //Voltar
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

    private static void menuGerenciarProjetos() {
        int subMenuOption = 0;

        do {
            MenuExibir.gerenciarProjetosMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1: //Cadastrar projeto
                        MenuAcoes.cadastrarProjeto();
                        break;
                    case 2: //Remover projeto
                        MenuAcoes.removerProjeto();
                        break;
                    case 3: //Listar projetos
                        MenuAcoes.listarProjetos();
                        break;
                    case 4: //Voltar
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

    private static void menuGerenciarTarefas() {
        int subMenuOption = 0;

        do {
            MenuExibir.gerenciarTarefasMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1: //Cadastrar tarefa
                        MenuAcoes.cadastrarTarefa();
                        break;
                    case 2: //Remover tarefa
                        MenuAcoes.removerTarefa();
                        break;
                    case 3: //Listar tarefas
                        MenuAcoes.listarTarefas();
                        break;
                    case 4: //Voltar
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

    private static void menuRelatoriosAtividades() {
        int subMenuOption = 0;

        do {
            MenuExibir.relatoriosAtividadesMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1: //Tempo total semanal
                        MenuAcoes.tempoTotalSemanal();
                        break;
                    case 2: //Tempo total mensal
                        MenuAcoes.tempoTotalMensal();
                        break;
                    case 3: //Voltar
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
        int subMenuOption = 0;

        do {
            MenuExibir.relatoriosIndicadoresMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1: //Media de tempo por dia
                        MenuAcoes.mediaTempoPorDia();
                        break;
                    case 2: //Media de tempo geral
                        MenuAcoes.mediaTempoGeral();
                        break;
                    case 3: //Ranking de tempo
                        MenuAcoes.rankingTempo();
                        break;
                    case 4: //Voltar
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