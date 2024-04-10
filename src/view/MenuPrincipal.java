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
                    case 1 -> MenuAcoes.planilhaDeHoras();            // Planilha de horas
                    case 2 -> menuRelatoriosAtividades();             // Relatórios de atividades
                    case 3 -> menuRelatoriosIndicadores();            // Relatórios de indicadores
                    case 4 -> menuGerenciarPessoas();                 // Gerenciar pessoas
                    case 5 -> menuGerenciarProjetos();                // Gerenciar projetos
                    case 6 -> menuGerenciarTarefas();                 // Gerenciar tarefas
                    case 7 -> System.out.println(MensagensMenu.SAIR_MENU.getMensagem());      // Sair
                    default -> System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (menuOption != 7);
    }

    private static void menuGerenciarPessoas() {
        int subMenuOption = 0;

        do {
            MenuExibir.gerenciarPessoasMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1 -> MenuAcoes.cadastrarPessoa();            // Cadastrar pessoa
                    case 2 -> MenuAcoes.listarPessoas();              // Listar pessoas
                    case 3 -> System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());   // Voltar
                    default -> System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (subMenuOption != 3);
    }

    private static void menuGerenciarProjetos() {
        int subMenuOption = 0;

        do {
            MenuExibir.gerenciarProjetosMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1 -> MenuAcoes.cadastrarProjeto();           // Cadastrar projeto
                    case 2 -> MenuAcoes.listarProjetos();             // Listar projetos
                    case 3 -> MenuAcoes.adicionarPessoaAProjeto();    // Adicionar Pessoa a Projeto
                    case 4 -> MenuAcoes.adicionarTarefaAProjeto();    // Adicionar Tarefa a Projeto
                    case 5 -> MenuAcoes.adicionarTagAProjeto();       // Adicionar Tag a Projeto
                    case 6 -> System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());   // Voltar
                    default -> System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (subMenuOption != 6);
    }

    private static void menuGerenciarTarefas() {
        int subMenuOption = 0;

        do {
            MenuExibir.gerenciarTarefasMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1 -> MenuAcoes.cadastrarTarefa();            // Cadastrar tarefa
                    case 2 -> MenuAcoes.listarTarefas();              // Listar tarefas
                    case 3 -> System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());   // Voltar
                    default -> System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (subMenuOption != 3);
    }

    private static void menuRelatoriosAtividades() {
        int subMenuOption = 0;

        do {
            MenuExibir.relatoriosAtividadesMenu();

            try {
                subMenuOption = scanner.nextInt();
                scanner.nextLine();

                switch (subMenuOption) {
                    case 1 -> MenuAcoes.tempoTotalSemanal();          // Tempo total semanal
                    case 2 -> MenuAcoes.tempoTotalMensal();           // Tempo total mensal
                    case 3 -> System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());   // Voltar
                    default -> System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
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
                    case 1 -> MenuAcoes.mediaTempoPorDia();           // Media de tempo por dia
                    case 2 -> MenuAcoes.mediaTempoGeral();            // Media de tempo geral
                    case 3 -> MenuAcoes.rankingTempo();               // Ranking de tempo
                    case 4 -> System.out.println(MensagensMenu.RETORNA_MENU.getMensagem());   // Voltar
                    default -> System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                }
            } catch (InputMismatchException e) {
                System.out.println(MensagensMenu.OPCAO_INVALIDA.getMensagem());
                scanner.nextLine();
            }

        } while (subMenuOption != 4);
    }
}