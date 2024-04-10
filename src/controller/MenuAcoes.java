package controller;

import model.Pessoa;
import repository.RepositoryPessoa;
import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;
import utility.EntradaHelper;
import utility.TipoCargo;
import utility.TipoPlano;
import utility.ValidadoresEntrada;

import java.util.Scanner;

public class MenuAcoes {

    static Scanner scanner = new Scanner(System.in);

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
        try {
            String input;

            String nome = ValidadoresEntrada.obterNomeValidado
                    (EntradaHelper.obterDado("Digite o nome: ", scanner));

            String username = ValidadoresEntrada.obterUsernameValidado
                    (EntradaHelper.obterDado("Digite o username: ", scanner), nome);

            String cpf = ValidadoresEntrada.obterCpfValidado
                    (EntradaHelper.obterDado("Digite o cpf: ", scanner));

            TipoCargo cargo = ValidadoresEntrada.obterCargoValidado
                    (EntradaHelper.obterDado("Digite o cargo: ", scanner));

            TipoPlano plano = ValidadoresEntrada.obterPlanoValidado
                    (EntradaHelper.obterDado("Digite o plano: ", scanner));

            Pessoa pessoa = new Pessoa.Builder()
                    .username(username)
                    .nome(nome)
                    .cpf(cpf)
                    .cargo(cargo)
                    .plano(plano)
                    .build();

            PessoaService.criarPessoa(pessoa);

            System.out.println("Pessoa cadastrada com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
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
        System.out.println(TarefaService.buscarTarefa(""));
    }

    public static void cadastrarProjeto() {
        //TODO
    }

    public static void listarProjetos() {
        System.out.println(ProjetoService.buscarProjeto(""));
    }
}