package controller;

import dto.PessoaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tag;
import model.Tarefa;
import utility.Conversores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import repository.RepositoryPessoa;
import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;
import utility.EntradaHelper;
import utility.TipoCargo;
import utility.TipoPlano;
import utility.ValidadoresEntrada;

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
        System.out.print("Digite o username do usuário: ");
        String pessoaUsername = scanner.nextLine();
        System.out.println("Digite o número referente ao mês desejado: ");
        int diaMes = 0;
        while (diaMes < 1 || diaMes > 12) {
            diaMes = Integer.parseInt(scanner.nextLine());
        }
        LocalDate dataMes = LocalDate.of(2024, diaMes, 1);
        MenuRelatorios.relatorioMensal(pessoaUsername, dataMes);
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
                    (EntradaHelper.obterDado("Digite o username: ", scanner));

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
        System.out.println(PessoaService.buscarPessoas(""));
    }

    public static void cadastrarTarefa() {
        try {
            String titulo = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite o título: ", scanner));

            String descricao = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite a descrição: ", scanner));

            PessoaDTO pessoa = Conversores.converterParaDTO
                    (PessoaService.buscarPessoa
                            (ValidadoresEntrada.obterUsernameValidado
                                    (EntradaHelper.obterDado("Digite o username: ", scanner))));

            LocalDateTime dataInicio = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de início: ", scanner));

            LocalDateTime dataFinal = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de final: ", scanner));

            Tag tag = ValidadoresEntrada.obterTagValidado
                    (EntradaHelper.obterDado("Digite a tag: ", scanner));

            Tarefa tarefa = new Tarefa.Builder()
                    .titulo(titulo)
                    .descricao(descricao)
                    .pessoaDTO(pessoa)
                    .dataHoraInicio(dataInicio)
                    .dataHoraInicio(dataFinal)
                    .tag(tag)
                    .build();

            TarefaService.criarTarefa(tarefa);

            System.out.println("Tarefa cadastrada com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarTarefas() {
        System.out.println(TarefaService.buscarTarefas(""));
    }

    public static void cadastrarProjeto() {
        try{
            String titulo = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite o título: ", scanner));

            String descricao = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite a descrição: ", scanner));

            LocalDateTime dataInicio = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de início: ", scanner));

            LocalDateTime dataFinal = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de final: ", scanner));

            PessoaDTO pessoa = Conversores.converterParaDTO
                    (PessoaService.buscarPessoa
                            (ValidadoresEntrada.obterUsernameValidado
                                    (EntradaHelper.obterDado("Digite o username do responsável: ", scanner))));

            List<PessoaDTO> pessoas = new ArrayList<>();
            pessoas.add(pessoa);

            Projeto projeto = new Projeto.Builder()
                    .titulo(titulo)
                    .descricao(descricao)
                    .pessoasDTO(pessoas)
                    .dataHoraInicio(dataInicio)
                    .dataHoraFim(dataFinal)
                    .build();

            ProjetoService.criarProjeto(projeto);

            System.out.println("Projeto criado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void adicionarPessoaAProjeto() {
        try {
            String titulo = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título do projeto: ", scanner));

            String username = ValidadoresEntrada
                    .obterUsernameValidado(EntradaHelper.obterDado("Digite o username da pessoa: ", scanner));

            boolean pessoaFoiAdicionada = ProjetoService.adicionarPessoa(titulo, username);

            String mensagem = pessoaFoiAdicionada
                    ? "Pessoa vinculada ao projeto com sucesso!"
                    : "Erro ao vincular pessoa, verifique os dados...";

            System.out.println(mensagem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void adicionarTarefaAProjeto() {
        try {
            String tituloProjeto = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título do projeto: ", scanner));

            String tituloTarefa = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título da tarefa: ", scanner));

            boolean tarefaFoiAdicionada = ProjetoService.adicionarTarefa(tituloProjeto, tituloTarefa);

            String mensagem = tarefaFoiAdicionada
                    ? "Tarefa adicionada ao projeto com sucesso!"
                    : "Erro ao adicionar tarefa, verifique os dados...";

            System.out.println(mensagem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void adicionarTagAProjeto() {
        try {
            String titulo = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título do projeto: ", scanner));

            Tag tituloTarefa = ValidadoresEntrada
                    .obterTagValidado(EntradaHelper.obterDado("Informe a tag: ", scanner));

            boolean tagFoiAdicionada = ProjetoService.adicionarTag(titulo, tituloTarefa);

            String mensagem = tagFoiAdicionada
                    ? "Tag adicionada ao projeto com sucesso!"
                    : "Erro ao adicionar Tag, verifique os dados...";

            System.out.println(mensagem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarProjetos() {
        System.out.println(ProjetoService.buscarProjetos(""));
    }
}